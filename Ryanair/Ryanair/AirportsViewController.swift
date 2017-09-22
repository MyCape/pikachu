//
//  AirportsViewController.swift
//  Ryanair
//
//  Created by GreatFeat on 22/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit
import PKHUD

class AirportsViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        getAirport()
    }

    //MARK: API
    func getAirport() {
        LoadingView.retrievingProgress()
        let request = AirportRequestManager()
        request.getAirportsData(completionHandler: { (json) in
            LoadingView.hide()
            print(json)
        }) { (error) in
            LoadingView.hide()
            self.showAlert(error: error as! String)
        }
    }

    //MARK: HELPER
    func showAlert(error: String) {
        let alert = UIAlertController(title: error, message: "", preferredStyle: UIAlertControllerStyle.alert)
        let okAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
        alert.addAction(okAction)
        self.present(alert, animated: true, completion: nil)
    }

}
