//
//  AirportModel.swift
//  Ryanair
//
//  Created by GreatFeat on 22/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import Foundation

fileprivate enum Key: String {
    case city = "city"
    case country = "country"
    case elevation = "elevation"
    case iata = "iata"
    case icao = "icao"
    case lat = "lat"
    case lon = "lon"
    case name = "name"
    case tz = "tz"
}

class AirportModel {
    var city = ""
    var country = ""
    var elevation = ""
    var iata = ""
    var icao = ""
    var lat = ""
    var lon = ""
    var name = ""
    var tz = ""

    func set(data: [String:AnyObject]) {
        self.city = data[Key.city.rawValue] as? String ?? ""
        self.country = data[Key.country.rawValue] as? String ?? ""
        self.elevation = data[Key.elevation.rawValue] as? String ?? ""
        self.iata = data[Key.iata.rawValue] as? String ?? ""
        self.icao = data[Key.icao.rawValue] as? String ?? ""
        self.lat = data[Key.lat.rawValue] as? String ?? ""
        self.lon = data[Key.lon.rawValue] as? String ?? ""
        self.name = data[Key.name.rawValue] as? String ?? ""
        self.tz = data[Key.tz.rawValue] as? String ?? ""
    }
}
