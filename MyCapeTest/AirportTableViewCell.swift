//
//  AirportTableViewCell.swift
//  MyCapeTest
//
//  Created by Mark Kevin King on 9/20/17.
//  Copyright © 2017 Mobext Inc. All rights reserved.
//

import UIKit

class AirportTableViewCell: UITableViewCell {
  var airport: Airport?
  
  @IBOutlet weak var codeLabel: UILabel!
  @IBOutlet weak var nameLabel: UILabel!
  @IBOutlet weak var cityLabel: UILabel!
  @IBOutlet weak var countryLabel: UILabel!
  
  override func awakeFromNib() {
    super.awakeFromNib()
    // Initialization code
  }
  
  override func setSelected(_ selected: Bool, animated: Bool) {
    super.setSelected(selected, animated: animated)
    
    // Configure the view for the selected state
  }
  
  func configureCell() {
    if let airport = airport {
      codeLabel.text = airport.code
      nameLabel.text = airport.title
      cityLabel.text = airport.city
      countryLabel.text = airport.country
    }
  }
  
}
