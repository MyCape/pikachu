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
    for airport in airports {
      mapView.addAnnotation(airport)
    }
    
    // Do any additional setup after loading the view.
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
  
  
  /*
   // MARK: - Navigation
   
   // In a storyboard-based application, you will often want to do a little preparation before navigation
   override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
   // Get the new view controller using segue.destinationViewController.
   // Pass the selected object to the new view controller.
   }
   */
  
  @objc func popUp() {
    let alertController = UIAlertController(title: "Options", message: "Select an option", preferredStyle: .actionSheet)
    let googleAction = UIAlertAction(title: "Google Search", style: .default) { (action) in
      let airportAnnotationView = self.selectedAnnotation
      let airport = self.searchAirportViaCode(code: airportAnnotationView!.code!)
      let title = airport?.title?.replacingOccurrences(of: " ", with: "%20")
      let url = "https://www.google.com/#q=Looking%20for%20" + title!
      UIApplication.shared.openURL(URL(string: url)!)
    }
    
    let removeAction = UIAlertAction(title: "Remove fron lists", style: .default) { (action) in
      let airportAnnotationView = self.selectedAnnotation
      let airport = self.searchAirportViaCode(code: airportAnnotationView!.code!)
      self.mapView.removeAnnotation(airport!)
    }
    alertController.addAction(googleAction)
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
