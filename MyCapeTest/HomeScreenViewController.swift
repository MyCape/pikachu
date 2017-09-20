//
//  HomeScreenViewController.swift
//  MyCapeTest
//
//  Created by Mark Kevin King on 9/20/17.
//  Copyright © 2017 Mobext Inc. All rights reserved.
//

import UIKit
import CoreLocation
import Alamofire
import SwiftyJSON

class HomeScreenViewController: UIViewController {
  var airports = [Airport]()
  
  var locationManager: CLLocationManager?
  var currentLocation = CLLocation()
  var isSorted = false
  
  @IBOutlet weak var tableView: UITableView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    tableView.delegate = self
    tableView.dataSource = self
    locationManager = CLLocationManager()
    let status  = CLLocationManager.authorizationStatus()
    if status == .notDetermined {
      self.locationManager?.requestWhenInUseAuthorization()
      self.locationManager?.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
      self.locationManager?.startUpdatingLocation()
      return
    }
    
    if status == .denied || status == .restricted {
      return
    }
    
    self.locationManager?.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
    self.locationManager?.startUpdatingLocation()
    self.locationManager?.delegate = self
    
    self.loadData()
  }
  
  func loadData() {
    Alamofire.request("https://raw.githubusercontent.com/mwgg/Airports/master/airports.json").responseJSON { (response) in
      let json = JSON(response.data!)
      var airports = [Airport]()
      for (_, subJson):(String, JSON) in json {
        let airport = Airport(name: subJson["name"].stringValue,
                              code: subJson["icao"].stringValue,
                              city: subJson["city"].stringValue,
                              country: subJson["country"].stringValue,
                              elevation: subJson["elevation"].intValue,
                              lat: subJson["lat"].doubleValue,
                              long: subJson["lon"].doubleValue,
                              timeZone: subJson["tz"].stringValue)
        airports.append(airport)
      }
      self.airports = airports
      self.tableView.reloadData()
    }
  }
}

extension HomeScreenViewController: CLLocationManagerDelegate {
  func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
    self.currentLocation = locations.last!
    print(locations.last)
    if isSorted == false && airports.count > 0 {
      airports.sort(by: { $0.distance(to: self.currentLocation) < $1.distance(to: self.currentLocation) })
      self.tableView.reloadData()
      isSorted = true
    }
    
  }
  
  func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
    print("Error \(error)")
  }
  
  func locationManager(_ manager: CLLocationManager,
                       didFinishDeferredUpdatesWithError error: Error?) {
    
  }
}


extension HomeScreenViewController: UITableViewDataSource {
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return airports.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let airport = airports[indexPath.row]
    let cell = tableView.dequeueReusableCell(withIdentifier: "AirportTableViewCell") as! AirportTableViewCell
    cell.airport = airport
    cell.configureCell()
    return cell
  }
}

extension HomeScreenViewController: UITableViewDelegate {
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 150
  }
  
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    let airport = airports[indexPath.row]
    let mapViewVC = self.storyboard?.instantiateViewController(withIdentifier: "MapViewViewController") as! MapViewViewController
    mapViewVC.selectedAirport = airport
    mapViewVC.airports = airports
    self.navigationController?.pushViewController(mapViewVC, animated: true)
  }
}
