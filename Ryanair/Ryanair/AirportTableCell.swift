//
//  AirportTableCell.swift
//  Ryanair
//
//  Created by GreatFeat on 22/09/2017.
//  Copyright © 2017 Ariel. All rights reserved.
//

import UIKit

class AirportTableCell: UITableViewCell {

    @IBOutlet weak var airportName: UILabel!
    @IBOutlet weak var location: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

    func setCell(data: AirportModel) {
        airportName.text = data.name
        location.text = "\(data.city), \(data.country)"
    }

}
