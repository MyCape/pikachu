//
//  SplashScreenViewController.swift
//  MyCapeTest
//
//  Created by Mark Kevin King on 9/20/17.
//  Copyright © 2017 Mobext Inc. All rights reserved.
//

import UIKit

class SplashScreenViewController: UIViewController {
  var timer = Timer()
  
  override func viewDidLoad() {
    super.viewDidLoad()
    // Do any additional setup after loading the view.
  }
  
  override func viewDidAppear(_ animated: Bool) {
    runTimer()
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
  
  func runTimer() {
    let timer = Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(SplashScreenViewController.updateTimer), userInfo: nil, repeats: false)
    timer.fire()
  }
  
  @objc func updateTimer() {
    let nv = self.storyboard?.instantiateViewController(withIdentifier: "NavigationController") as! UINavigationController
    self.present(nv, animated: true, completion: nil)
  }
  
  
  /*
   // MARK: - Navigation
   
   // In a storyboard-based application, you will often want to do a little preparation before navigation
   override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
   // Get the new view controller using segue.destinationViewController.
   // Pass the selected object to the new view controller.
   }
   */
  
}
