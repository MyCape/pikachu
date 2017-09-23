//
//  LaunchScreen.swift
//  Ryanair
//
//  Created by GreatFeat on 21/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit
import PKHUD

class LaunchScreen: UIViewController {

    @IBOutlet weak var plane: UIImageView!

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        plane.startRotating(duration: 2.5)
        let when = DispatchTime.now() + 2
        DispatchQueue.main.asyncAfter(deadline: when) {
            self.plane.stopRotating()
            self.performSegue(withIdentifier: "segueToMaps", sender: self)
        }
    }
}
