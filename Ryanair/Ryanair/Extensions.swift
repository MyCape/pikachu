//
//  Extensions.swift
//  Ryanair
//
//  Created by GreatFeat on 22/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import Foundation
import UIKit

extension UIView {
    func startRotating(duration: Double) {
        let kAnimationKey = "rotation"

        if self.layer.animation(forKey: kAnimationKey) == nil {
            let animate = CABasicAnimation(keyPath: "transform.rotation")
            animate.duration = duration
            animate.repeatCount = Float.infinity
            animate.fromValue = 0.0
            animate.toValue = Float(Double.pi * 2.0)
            self.layer.add(animate, forKey: kAnimationKey)
        }
    }
    func stopRotating() {
        let kAnimationKey = "rotation"

        if self.layer.animation(forKey: kAnimationKey) != nil {
            self.layer.removeAnimation(forKey: kAnimationKey)
        }
    }
}
