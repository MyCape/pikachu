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
    func getAirportsData(completionHandler: @escaping (_ response: [AirportModel]) -> Swift.Void,
                         errorHandler: @escaping (Error) -> Swift.Void) -> Void  {
        Alamofire.request(API.url.rawValue).responseJSON { response in
            var airports = [AirportModel]()
            if let jsonData = response.data {
                let dict = try? JSONSerialization.jsonObject(with: jsonData, options: [])
                for (_, value) in (dict as? [String: AnyObject])! {
                    let data = value as! [String: AnyObject]
                    let countryCode = (Locale.current as NSLocale).object(forKey: .countryCode) as? String
                    if data["country"] as? String == countryCode {
                        let model = AirportModel()
                        model.set(data: data)
                        airports.append(model)
                    }
                }
                completionHandler(airports)
            }

            if let error = response.error {
                errorHandler(error)
            }
        }
    }
}
