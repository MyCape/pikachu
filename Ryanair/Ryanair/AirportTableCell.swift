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
        let constants = Constants()
        airportName.text = data.name
        if data.city.isEmpty {
            location.text = data.country
        } else {
            location.text = "\(data.city), \(data.country)"
        }
        self.contentView.backgroundColor = UIColor.clear
        if let savedIcao = UserDefaults.standard.object(forKey: constants.savedIcaoKey) as? String {
            if savedIcao == data.icao {
                self.contentView.backgroundColor = UIColor.lightGray
            }
        }
    }

}
