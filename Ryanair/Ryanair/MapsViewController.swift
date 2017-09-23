//
//  MapsViewController.swift
//  Ryanair
//
//  Created by GreatFeat on 23/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit
import GoogleMaps

class MapsViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        addMap()
    }

    //MARK: HELPERS
    func addMap() {

        //creating a camera
        let camera = GMSCameraPosition.camera(withLatitude: 23.431351, longitude: 85.325879, zoom: 6.0)

        //this is our map view
        let mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)

        //adding mapview to view
        view = mapView

        //creating a marker on the map
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: 23.431351, longitude: 85.325879)
        marker.title = "Ranchi, Jharkhand"
        marker.map = mapView
    }

}
