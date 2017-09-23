//
//  MapsViewController.swift
//  Ryanair
//
//  Created by GreatFeat on 23/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit
import GoogleMaps
import CoreLocation

class MapsViewController: UIViewController {

    //MARK: PROPERTIES
    var userLocation = CLLocation()
    var locationManager = CLLocationManager()
    var marker = GMSMarker()
    var center = CLLocationCoordinate2D()
    var mapView: GMSMapView!
    var airports = [AirportModel]()

    override func viewDidLoad() {
        super.viewDidLoad()
        //creating a camera
        addMap()
        getUserLocation()
        getAirport()
    }

    //MARK: HELPERS
    func addMap() {
        let camera = GMSCameraPosition.camera(withLatitude: 14.5547, longitude: 121.0244, zoom: 13.0)
        mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
        mapView.isMyLocationEnabled = true
        view = mapView
        center = CLLocationCoordinate2D(latitude: 14.5547, longitude: 121.0244)
        marker.position = center
        marker.map = mapView
    }

    func getUserLocation() {
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }

    func showAlert(error: String) {
        let alert = UIAlertController(title: error, message: "", preferredStyle: UIAlertControllerStyle.alert)
        let okAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
        alert.addAction(okAction)
        self.present(alert, animated: true, completion: nil)
    }

    func addMarkers() {
        for i in 0..<airports.count {
            let location = CLLocationCoordinate2D(latitude: airports[i].lat, longitude: airports[i].lon)
            let anotherMarker = GMSMarker()
            anotherMarker.position = location
            anotherMarker.title = airports[i].name
            anotherMarker.map = mapView
        }
    }

    //MARK: API
    func getAirport() {
        LoadingView.retrievingProgress()
        let request = AirportRequestManager()
        request.getAirportsData(completionHandler: { (airports) in
            LoadingView.hide()
            self.airports = airports
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

        let camera = GMSCameraPosition.camera(withLatitude: userLocation.coordinate.latitude,
                                              longitude: userLocation.coordinate.longitude, zoom: 13.0)
        mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
        mapView.isMyLocationEnabled = true
        self.view = mapView
        marker.position = center
        marker.map = mapView

        locationManager.stopUpdatingLocation()
    }
}
