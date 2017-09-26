//
//  AirportsViewController.swift
//  Ryanair
//
//  Created by Chris on 22/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit
import PKHUD
import CoreLocation

class AirportsViewController: BaseViewController {

    //MARK: OUTLETS
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var revealerMap: UIBarButtonItem!

    //MARK: PROPERTIES
    var airports = [AirportModel]()
    var userLocation = CLLocation()
    var locationManager = CLLocationManager()
    var center = CLLocationCoordinate2D()

    override func viewDidLoad() {
        super.viewDidLoad()
        if self.revealViewController() != nil {
            revealerMap.target = self.revealViewController()
            revealerMap.action = #selector(SWRevealViewController.rightRevealToggle(_:))
            self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
            self.revealViewController().rightViewRevealWidth = self.view.frame.size.width * 0.95
        }
        getUserLocation()
        getAirport()
    }

    //MARK: API
    func getAirport() {
        LoadingView.retrievingProgress()
        let request = AirportRequestManager()
        request.getAirportsData(completionHandler: { (airports) in
            LoadingView.hide()
            self.sortAirports(airportsModelArray: airports)
            self.tableView.reloadData()
        }) { (error) in
            LoadingView.hide()
            self.showAlert(error: error as! String)
        }
    }

    //MARK: HELPER
    func sortAirports(airportsModelArray: [AirportModel]) {
        for i in 0..<airportsModelArray.count {
            let airportCoordinates = CLLocation(latitude: airportsModelArray[i].lat, longitude: airportsModelArray[i].lon)
            let distance = userLocation.distance(from: airportCoordinates)
            if distance < 300000.00 {
                self.airports.append(airportsModelArray[i])
            }
        }
    }

    func getUserLocation() {
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }
}

extension AirportsViewController: UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.airports.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tableCell = tableView.dequeueReusableCell(withIdentifier: "AirportTableCell", for: indexPath) as! AirportTableCell
        tableCell.setCell(data: self.airports[indexPath.row])
        return tableCell
    }
}

extension AirportsViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        showOptionMenu(airport: airports[indexPath.row].name, icao: airports[indexPath.row].icao) { (shouldReload, shouldShowError) in
            if shouldReload {
                tableView.reloadData()
            }

            if shouldShowError {
                self.showAlert(error: "Only one Airport can be saved to favorite.")
            }
        }
    }
}

extension AirportsViewController: CLLocationManagerDelegate {
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        userLocation = locations.last!
        center = CLLocationCoordinate2D(latitude: userLocation.coordinate.latitude, longitude: userLocation.coordinate.longitude)
        locationManager.stopUpdatingLocation()
    }
}
