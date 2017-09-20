//
//  Airport.swift
//  MyCapeTest
//
//  Created by Mark Kevin King on 9/20/17.
//  Copyright © 2017 Mobext Inc. All rights reserved.
//

import Foundation
import CoreLocation
import MapKit

class Airport: NSObject, MKAnnotation {
  var title: String?
  var code: String
  var city: String
  var country: String
  var elevation: Int
  var lat: Double
  var long: Double
  var timeZone: String
  var coordinate: CLLocationCoordinate2D
  
  var location: CLLocation {
    return CLLocation(latitude: self.lat, longitude: self.long)
  }
  
  init(name: String, code: String, city: String, country: String, elevation: Int, lat: Double, long: Double, timeZone: String) {
    self.title = name
    self.code = code
    self.city = city
    self.country = country
    self.elevation = elevation
    self.lat = lat
    self.long = long
    self.coordinate = CLLocationCoordinate2D(latitude: lat, longitude: long)
    self.timeZone = timeZone
  }
  
  func distance(to location: CLLocation) -> CLLocationDistance {
    return location.distance(from: self.location)
  }
}
