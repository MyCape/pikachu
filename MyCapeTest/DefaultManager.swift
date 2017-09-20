//
//  DefaultManager.swift
//  MyCapeTest
//
//  Created by Mark Kevin King on 9/21/17.
//  Copyright © 2017 Mobext Inc. All rights reserved.
//

import Foundation

class DefaultManager {
  static let shared = DefaultManager()
  private let code = "code"
  
  
  let userDefault = UserDefaults.standard
  
  func saveCode(_ email: String?) {
    DispatchQueue.main.async {
      self.userDefault.set(email, forKey: self.code)
      self.userDefault.synchronize()
    }
  }
  
  func remove() {
    let defaults = UserDefaults.standard
    let dictionary = defaults.dictionaryRepresentation()
    dictionary.keys.forEach { key in
      defaults.removeObject(forKey: key)
    }
    defaults.synchronize()
  }
  
  func fetchCode() -> String? {
    let code = userDefault.object(forKey: self.code) as? String
    return code
  }
  
}

