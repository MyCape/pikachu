//
//  MapViewViewController.swift
//  MyCapeTest
//
//  Created by Mark Kevin King on 9/20/17.
//  Copyright © 2017 Mobext Inc. All rights reserved.
//

import UIKit
import MapKit

class MapViewViewController: UIViewController {
  var airports = [Airport]()
  var selectedAirport: Airport?
  var selectedAnnotation: AirportPinAnnotationView?
  
  @IBOutlet weak var mapView: MKMapView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    mapView.delegate = self
    mapView.showsUserLocation = true
    for airport in airports {
      mapView.addAnnotation(airport)
    }
    let viewRegion = MKCoordinateRegionMakeWithDistance((selectedAirport?.coordinate)!, 100, 100)
    mapView.setRegion(viewRegion, animated: true)
    // Do any additional setup after loading the view.
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
  
  
  @objc func popUp() {
    let alertController = UIAlertController(title: "Options", message: "Select an option", preferredStyle: .actionSheet)
    let googleAction = UIAlertAction(title: "Google Search", style: .default) { (action) in
      let airportAnnotationView = self.selectedAnnotation
      let airport = self.searchAirportViaCode(code: airportAnnotationView!.code!)
      let title = airport?.title?.replacingOccurrences(of: " ", with: "%20")
      let url = "https://www.google.com/#q=Looking%20for%20" + title!
      UIApplication.shared.openURL(URL(string: url)!)
    }
    
    let favoriteAction = UIAlertAction(title: "Favorite", style: .default) { (action) in
      let airportAnnotationView = self.selectedAnnotation
      let airport = self.searchAirportViaCode(code: airportAnnotationView!.code!)
      if let code = DefaultManager.shared.fetchCode() {
        let al = UIAlertController(title: "Message", message: "You already favorited one", preferredStyle: .alert)
        let okayAction = UIAlertAction(title: "Okay", style: .default, handler: nil)
        al.addAction(okayAction)
        self.present(al, animated: true, completion: nil)
      } else {
        DefaultManager.shared.saveCode(airport?.code)
      }
    }
    
    let removeAction = UIAlertAction(title: "Unfavorite all", style: .default) { (action) in
      DefaultManager.shared.remove()
    }
    alertController.addAction(googleAction)
    alertController.addAction(favoriteAction)
    alertController.addAction(removeAction)
    self.present(alertController, animated: true, completion: nil)
  }
  
  func searchAirportViaCode(code: String) -> Airport? {
    for airport in airports {
      if airport.code == code {
        return airport
      }
    }
    return nil
  }
}

extension MapViewViewController: MKMapViewDelegate {
  func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
    if let annotation = annotation as? Airport {
      let identifier = "pin"
      var view: AirportPinAnnotationView
      if let dequeuedView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier)
        as? AirportPinAnnotationView { // 2
        dequeuedView.annotation = annotation
        view = dequeuedView
      } else {
        // 3
        view = AirportPinAnnotationView(annotation: annotation, reuseIdentifier: identifier)
        view.canShowCallout = true
        view.calloutOffset = CGPoint(x: -5, y: 5)
        view.animatesDrop = true
        view.pinTintColor = UIColor.random()
        view.code = annotation.code
        let button = UIButton(type: .detailDisclosure)
        button.addTarget(self, action: #selector(MapViewViewController.popUp), for: .touchDown)
        view.rightCalloutAccessoryView = button
      }
      return view
    }
    return nil
  }
  
  func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {
    self.selectedAnnotation = view as! AirportPinAnnotationView
  }
}

extension UIColor{
  static func random() -> UIColor {
    return UIColor(red:   .random(),
                   green: .random(),
                   blue:  .random(),
                   alpha: 1.0)
  }
}

extension CGFloat {
  static func random() -> CGFloat {
    return CGFloat(arc4random()) / CGFloat(UInt32.max)
  }
}
