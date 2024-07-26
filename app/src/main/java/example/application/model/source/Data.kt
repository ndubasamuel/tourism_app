package example.application.model.source

import example.application.R
import example.application.model.UseData

class Data {


    fun strings(): ArrayList<UseData.TopScroll> {
        val strings = ArrayList<UseData.TopScroll>()
        strings.add(UseData.TopScroll("CABS"))
        strings.add(UseData.TopScroll("ACCOMMODATIONS"))
        strings.add(UseData.TopScroll("SAFARIS"))
        strings.add(UseData.TopScroll("$#EXCHANGE"))
        strings.add(UseData.TopScroll("FLIGHTS"))

        return strings
    }

    fun cabStrings(): ArrayList<UseData.CabScroll> {
        val strings = ArrayList<UseData.CabScroll>()
        strings.add(UseData.CabScroll("Enjoy your Ride"))


        return strings
    }

    fun accommodationImage(): ArrayList<UseData.ImageScroll> {
        val image = ArrayList<UseData.ImageScroll>()
        image.add(UseData.ImageScroll(R.drawable.kisumu))

        return image
    }


}