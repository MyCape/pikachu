//
//  AirportRequestManager.swift
//  Ryanair
//
//  Created by GreatFeat on 22/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import Foundation
import Alamofire

fileprivate enum API: String {
    case url = "https://raw.githubusercontent.com/mwgg/Airports/master/airports.json"
}

class AirportRequestManager {
    func getAirportsData(completionHandler: @escaping (_ response: Any) -> Swift.Void,
                         errorHandler: @escaping (Error) -> Swift.Void) -> Void  {
        Alamofire.request(API.url.rawValue).responseJSON { response in
            print("Request: \(String(describing: response.request))")
            print("Response: \(String(describing: response.response))")
            print("Result: \(response.result)")

            if let json = response.result.value {
                completionHandler(json)
            }

            if let error = response.error {
                errorHandler(error)
            }
        }
    }
}
