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

    override func viewDidLoad() {
        super.viewDidLoad()

        LoadingView.nativeProgress()

        let when = DispatchTime.now() + 2
        DispatchQueue.main.asyncAfter(deadline: when) {
            LoadingView.hide()
        }
    }
}
