//
//  BaseViewController.swift
//  Ryanair
//
//  Created by GreatFeat on 24/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit

fileprivate enum actionKey: String {
    case removeSaved = "Remove Saved"
    case saveToFavorite = "Save to Favorite"
    case doSomething = "Do Something"
    case searchInGoogle = "Search in Google"
    case cancel = "Cancel"
}

class BaseViewController: UIViewController {

    let constants = Constants()

    override func viewDidLoad() {
        super.viewDidLoad()

    }

    func showAlert(error: String) {
        let alert = UIAlertController(title: error, message: "", preferredStyle: UIAlertControllerStyle.alert)
        let okAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
        alert.addAction(okAction)
        self.present(alert, animated: true, completion: nil)
    }

    func saveToUserDefaults(item: Any ,key: String) {
        UserDefaults.standard.set(item, forKey: key)
        UserDefaults.standard.synchronize()
    }

    func getFromUserDefault(key: String) -> Any? {
        return UserDefaults.standard.object(forKey: key)

    }

    func removeFromUserDefaults(key: String) {
        UserDefaults.standard.removeObject(forKey: key)
        UserDefaults.standard.synchronize()
    }

    func showOptionMenu(airport: String, icao: String, completionHandler: @escaping (_ shouldReload: Bool,_ showError: Bool) -> Swift.Void) {
        var whatToDo = String()
        var savedAirportIcao = String()
        if let savedIcao = getFromUserDefault(key: constants.savedIcaoKey) as? String {
            savedAirportIcao = savedIcao
        }

        if savedAirportIcao == icao {
            whatToDo = actionKey.removeSaved.rawValue
        } else if savedAirportIcao != icao {
            whatToDo = actionKey.saveToFavorite.rawValue
        }

        let optionMenu = UIAlertController(title: nil, message: actionKey.doSomething.rawValue, preferredStyle: .actionSheet)

        let saveOrRemoveAction = UIAlertAction(title: whatToDo, style: .default, handler: {
            (alert: UIAlertAction!) -> Void in
            if whatToDo == actionKey.saveToFavorite.rawValue && savedAirportIcao.isEmpty {
                self.saveToUserDefaults(item: icao, key: self.constants.savedIcaoKey)
                completionHandler(true, false)
            } else if whatToDo == actionKey.removeSaved.rawValue {
                self.removeFromUserDefaults(key: self.constants.savedIcaoKey)
                completionHandler(true, false)
            } else if whatToDo == actionKey.saveToFavorite.rawValue && !savedAirportIcao.isEmpty {
                completionHandler(false, true)
            }

        })

        let searchAction = UIAlertAction(title: actionKey.searchInGoogle.rawValue, style: .default, handler: {
            (alert: UIAlertAction!) -> Void in
            let query = airport.replacingOccurrences(of: " ", with: "")
            UIApplication.shared.open(NSURL(string: "https://www.google.com/#q=\(query)")! as URL)
        })

        let cancelAction = UIAlertAction(title: actionKey.cancel.rawValue, style: .default, handler: {
            (alert: UIAlertAction!) -> Void in
            //do none
        })


        optionMenu.addAction(saveOrRemoveAction)
        optionMenu.addAction(searchAction)
        optionMenu.addAction(cancelAction)

        self.present(optionMenu, animated: true, completion: nil)
    }
}
