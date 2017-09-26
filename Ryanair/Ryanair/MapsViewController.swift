//
//  MapsViewController.swift
//  Ryanair
//
//  Created by Chris on 23/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit
import GoogleMaps
import CoreLocation

class MapsViewController: BaseViewController {

    //MARK: PROPERTIES
    var userLocation = CLLocation()
    var locationManager = CLLocationManager()
    var center = CLLocationCoordinate2D()
    var marker = GMSMarker()
    var mapView: GMSMapView!
    var airports = [AirportModel]()

    override func viewDidLoad() {
        super.viewDidLoad()
        getUserLocation()
        getAirport()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        addMarkers()
    }

    //MARK: HELPERS
    func getUserLocation() {
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }

    func addMap() {
        let camera = GMSCameraPosition.camera(withLatitude: userLocation.coordinate.latitude,
                                              longitude: userLocation.coordinate.longitude, zoom: 13.0)
        mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
        mapView.isMyLocationEnabled = true
        self.view = mapView
        marker.position = center
        marker.map = mapView
    }

    func addMarkers() {
        var icao = String()
        if let savedIcao = getFromUserDefault(key: self.constants.savedIcaoKey) as? String {
            icao = savedIcao
        }
        for i in 0..<airports.count {
            let location = CLLocationCoordinate2D(latitude: airports[i].lat, longitude: airports[i].lon)
            let anotherMarker = GMSMarker()
            if icao == airports[i].icao {
                anotherMarker.icon = GMSMarker.markerImage(with: .blue)
            } else {
                anotherMarker.icon = GMSMarker.markerImage(with: .red)
            }
            anotherMarker.position = location
            anotherMarker.title = airports[i].name
            anotherMarker.map = mapView
        }
    }

    func sortAirports(airportsModelArray: [AirportModel]) {
        for i in 0..<airportsModelArray.count {
            let airportCoordinates = CLLocation(latitude: airportsModelArray[i].lat, longitude: airportsModelArray[i].lon)
            let distance = userLocation.distance(from: airportCoordinates)
            if distance < 300000.00 {
                self.airports.append(airportsModelArray[i])
            }
        }
    }

    //MARK: API
    func getAirport() {
        LoadingView.retrievingProgress()
        let request = AirportRequestManager()
        request.getAirportsData(completionHandler: { (airports) in
            LoadingView.hide()
            self.sortAirports(airportsModelArray: airports)
            self.addMarkers()
        }) { (error) in
            LoadingView.hide()
            self.showAlert(error: error as! String)
        }
    }
}

extension MapsViewController: CLLocationManagerDelegate {
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        userLocation = locations.last!
        center = CLLocationCoordinate2D(latitude: userLocation.coordinate.latitude, longitude: userLocation.coordinate.longitude)
        addMap()
        locationManager.stopUpdatingLocation()
    }
}
