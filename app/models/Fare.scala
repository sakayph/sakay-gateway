/*
 * Copyright 2013 Thomas Dy under the terms of the MIT license
 * located at https://raw.github.com/thatsmydoing/sakay-gateway/master/LICENSE
 */
package models

case class BusFare(regular: Double, discounted: Double)

object BusFare {
  val fares = Map(
    "pub_aircon" -> List(
      BusFare(0, 0),
      BusFare(12.00, 9.50),
      BusFare(12.00, 9.50),
      BusFare(12.00, 9.50),
      BusFare(12.00, 9.50),
      BusFare(12.00, 9.50),
      BusFare(14.25, 11.25),
      BusFare(16.50, 13.00),
      BusFare(18.50, 15.00),
      BusFare(20.75, 16.75),
      BusFare(23.00, 18.50),
      BusFare(25.25, 20.25),
      BusFare(27.50, 22.00),
      BusFare(29.50, 23.75),
      BusFare(31.75, 25.50),
      BusFare(34.00, 27.25),
      BusFare(36.25, 29.00),
      BusFare(38.50, 30.75),
      BusFare(40.50, 32.50),
      BusFare(42.75, 34.25),
      BusFare(45.00, 36.00),
      BusFare(47.25, 37.75),
      BusFare(49.50, 39.50),
      BusFare(51.50, 41.25),
      BusFare(53.75, 43.00),
      BusFare(56.00, 44.75),
      BusFare(58.25, 46.50),
      BusFare(60.50, 48.25),
      BusFare(62.50, 50.00),
      BusFare(64.75, 51.75),
      BusFare(67.00, 53.50),
      BusFare(69.25, 55.25),
      BusFare(71.50, 57.00),
      BusFare(73.50, 59.00),
      BusFare(75.75, 60.75),
      BusFare(78.00, 62.50),
      BusFare(80.25, 64.25),
      BusFare(82.50, 66.00),
      BusFare(84.50, 67.75),
      BusFare(86.75, 69.50),
      BusFare(89.00, 71.25),
      BusFare(91.25, 73.00),
      BusFare(93.50, 74.75),
      BusFare(95.50, 76.50),
      BusFare(97.75, 78.25),
      BusFare(100.00, 80.00),
      BusFare(102.25, 81.75),
      BusFare(104.50, 83.50),
      BusFare(106.50, 85.25),
      BusFare(108.75, 87.00),
      BusFare(111.00, 88.75),
      BusFare(113.25, 90.50),
      BusFare(115.50, 92.25),
      BusFare(117.50, 94.00),
      BusFare(119.75, 95.75),
      BusFare(122.00, 97.50),
      BusFare(124.25, 99.25),
      BusFare(126.50, 101.00),
      BusFare(128.50, 103.00),
      BusFare(130.75, 104.75),
      BusFare(133.00, 106.50)
    ),
    "pub_ordinary" -> List(
      BusFare(0, 0),
      BusFare(10.00, 8.00),
      BusFare(10.00, 8.00),
      BusFare(10.00, 8.00),
      BusFare(10.00, 8.00),
      BusFare(10.00, 8.00),
      BusFare(11.75, 9.50),
      BusFare(13.75, 11.00),
      BusFare(15.50, 12.50),
      BusFare(17.50, 14.00),
      BusFare(19.25, 15.50),
      BusFare(21.00, 17.00),
      BusFare(23.00, 18.25),
      BusFare(24.75, 19.75),
      BusFare(26.75, 21.25),
      BusFare(28.50, 22.75),
      BusFare(30.25, 24.25),
      BusFare(32.25, 25.75),
      BusFare(34.00, 27.25),
      BusFare(36.00, 28.75),
      BusFare(37.75, 30.25),
      BusFare(39.50, 31.75),
      BusFare(41.50, 33.25),
      BusFare(43.25, 34.75),
      BusFare(45.25, 36.00),
      BusFare(47.00, 37.50),
      BusFare(48.75, 39.00),
      BusFare(50.75, 40.50),
      BusFare(52.50, 42.00),
      BusFare(54.50, 43.50),
      BusFare(56.25, 45.00),
      BusFare(58.00, 46.50),
      BusFare(60.00, 48.00),
      BusFare(61.75, 49.50),
      BusFare(63.75, 51.00),
      BusFare(65.50, 52.50),
      BusFare(67.25, 54.00),
      BusFare(69.25, 55.25),
      BusFare(71.00, 56.75),
      BusFare(73.00, 58.25),
      BusFare(74.75, 59.75),
      BusFare(76.50, 61.25),
      BusFare(78.50, 62.75),
      BusFare(80.25, 64.25),
      BusFare(82.25, 65.75),
      BusFare(84.00, 67.25),
      BusFare(85.75, 68.75),
      BusFare(87.75, 70.25),
      BusFare(89.50, 71.75),
      BusFare(91.50, 73.00),
      BusFare(93.25, 74.50),
      BusFare(95.00, 76.00),
      BusFare(97.00, 77.50),
      BusFare(98.75, 79.00),
      BusFare(100.75, 80.50),
      BusFare(102.50, 82.00),
      BusFare(104.25, 83.50),
      BusFare(106.25, 85.00),
      BusFare(108.00, 86.50),
      BusFare(110.00, 88.00),
      BusFare(111.75, 89.50)
    ),
    "puj" -> List(
      BusFare(0, 0),
      BusFare(8.00, 6.50),
      BusFare(8.00, 6.50),
      BusFare(8.00, 6.50),
      BusFare(8.00, 6.50),
      BusFare(9.50, 7.50),
      BusFare(10.75, 8.75),
      BusFare(12.25, 9.75),
      BusFare(13.50, 11.00),
      BusFare(15.00, 12.00),
      BusFare(16.50, 13.25),
      BusFare(17.75, 14.25),
      BusFare(19.25, 15.50),
      BusFare(20.50, 16.50),
      BusFare(22.00, 17.75),
      BusFare(23.50, 18.75),
      BusFare(24.75, 19.75),
      BusFare(26.25, 21.00),
      BusFare(27.50, 22.25),
      BusFare(29.00, 23.25),
      BusFare(30.50, 24.50),
      BusFare(31.75, 25.50),
      BusFare(33.25, 26.75),
      BusFare(34.50, 27.75),
      BusFare(36.00, 28.75),
      BusFare(37.50, 30.00),
      BusFare(38.75, 31.00),
      BusFare(40.25, 32.25),
      BusFare(41.50, 33.25),
      BusFare(43.00, 34.50),
      BusFare(44.50, 35.50),
      BusFare(45.75, 36.75),
      BusFare(47.25, 37.75),
      BusFare(48.50, 38.75),
      BusFare(50.00, 40.00),
      BusFare(51.50, 41.25),
      BusFare(52.75, 42.25),
      BusFare(54.25, 43.50),
      BusFare(55.50, 44.50),
      BusFare(57.00, 45.75),
      BusFare(58.50, 46.75),
      BusFare(59.75, 47.75),
      BusFare(61.25, 49.00),
      BusFare(62.50, 50.00),
      BusFare(64.00, 51.25),
      BusFare(65.50, 52.50),
      BusFare(66.75, 53.50),
      BusFare(68.25, 54.75),
      BusFare(69.50, 55.75),
      BusFare(71.00, 56.75),
      BusFare(72.50, 58.00)
    )
  )

  def get(typ: String, distance: Double) = {
    fares(typ)(Math.ceil(distance / 1000).toInt)
  }
}

case class RailFare(regular: Double, stored: Double)

case class RailLine(stations: List[String], fares: List[RailFare]) {
  def has(station: String) = stations.contains(station)

  def getFare(from: String, to: String) = {
    val fromIndex = stations.indexOf(from)
    val toIndex = stations.indexOf(to)
    fares(Math.abs(fromIndex - toIndex))
  }
}

object RailFare {
  def apply(regular: Double): RailFare = RailFare(regular, regular)

  val lrt1 = RailLine(
    List(
      "LTFRB_4944", // Baclaran LRT
      "LTFRB_4945", // EDSA LRT
      "LTFRB_4946", // Libertad LRT
      "LTFRB_4947", // Gil Puyat LRT
      "LTFRB_4948", // Vito Cruz LRT
      "LTFRB_4949", // Quirino Ave LRT
      "LTFRB_4950", // Pedro Gil LRT
      "LTFRB_4951", // UN Ave LRT
      "LTFRB_4952", // Central Terminal LRT
      "LTFRB_4953", // Carriedo LRT
      "LTFRB_4954", // Doroteo Jose LRT
      "LTFRB_4955", // Bambang LRT
      "LTFRB_4956", // Tayuman LRT
      "LTFRB_4957", // Blumentritt LRT
      "LTFRB_4958", // Abad Santos LRT
      "LTFRB_4959", // R. Papa LRT
      "LTFRB_4960", // 5th Ave LRT
      "LTFRB_4961"  // Monumento LRT
    ),
    List(
      RailFare(0, 0),
      RailFare(12, 12),
      RailFare(12, 12),
      RailFare(12, 12),
      RailFare(12, 12),
      RailFare(15, 13),
      RailFare(15, 13),
      RailFare(15, 13),
      RailFare(15, 13),
      RailFare(15, 14),
      RailFare(15, 14),
      RailFare(15, 14),
      RailFare(15, 14),
      RailFare(15, 15),
      RailFare(15, 15),
      RailFare(15, 15),
      RailFare(15, 15),
      RailFare(15, 15)
    )
  )

  val lrt1Extended = RailLine(
    List(
      "LTFRB_4944", // Baclaran LRT
      "LTFRB_4945", // EDSA LRT
      "LTFRB_4946", // Libertad LRT
      "LTFRB_4947", // Gil Puyat LRT
      "LTFRB_4948", // Vito Cruz LRT
      "LTFRB_4949", // Quirino Ave LRT
      "LTFRB_4950", // Pedro Gil LRT
      "LTFRB_4951", // UN Ave LRT
      "LTFRB_4952", // Central Terminal LRT
      "LTFRB_4953", // Carriedo LRT
      "LTFRB_4954", // Doroteo Jose LRT
      "LTFRB_4955", // Bambang LRT
      "LTFRB_4956", // Tayuman LRT
      "LTFRB_4957", // Blumentritt LRT
      "LTFRB_4958", // Abad Santos LRT
      "LTFRB_4959", // R. Papa LRT
      "LTFRB_4960", // 5th Ave LRT
      "LTFRB_4961", // Monumento LRT
      "LTFRB_4962", // LRT Balintawak
      "LTFRB_4963"  // Roosevelt LRT
    ),
    List(
      RailFare(0, 0),
      RailFare(15, 13),
      RailFare(15, 13),
      RailFare(15, 14),
      RailFare(15, 14),
      RailFare(15, 15),
      RailFare(15, 15),
      RailFare(15, 15),
      RailFare(20, 16),
      RailFare(20, 16),
      RailFare(20, 16),
      RailFare(20, 17),
      RailFare(20, 17),
      RailFare(20, 17),
      RailFare(20, 18),
      RailFare(20, 18),
      RailFare(20, 18),
      RailFare(20, 19),
      RailFare(20, 19),
      RailFare(20, 20)
    )
  )

  val lrt2 = RailLine(
    List(
      "LTFRB_4977", // Recto LRT
      "LTFRB_4978", // Legarda LRT
      "LTFRB_4979", // Pureza LRT
      "LTFRB_4980", // V. Mapa LRT
      "LTFRB_4981", // J. Ruiz LRT
      "LTFRB_4982", // Gilmore LRT
      "LTFRB_4983", // Betty Go Belmonte LRT
      "LTFRB_4984", // Cubao LRT
      "LTFRB_4985", // Anonas LRT
      "LTFRB_4986", // Katipunan LRT
      "LTFRB_4987"  // Santolan LRT
    ),
    List(
      RailFare(0),
      RailFare(12),
      RailFare(12),
      RailFare(12),
      RailFare(13),
      RailFare(13),
      RailFare(13),
      RailFare(14),
      RailFare(14),
      RailFare(14),
      RailFare(15)
    )
  )

  val mrt3 = RailLine(
    List(
      "STOP_880847", // North Avenue MRT
      "LTFRB_4965", // Quezon MRT
      "LTFRB_4966", // Kamuning MRT
      "LTFRB_4967", // Cubao MRT
      "LTFRB_4968", // Santolan MRT
      "LTFRB_4969", // Ortigas MRT
      "LTFRB_4970", // Shaw MRT
      "LTFRB_4971", // Boni MRT
      "LTFRB_4972", // Guadalupe MRT
      "LTFRB_4973", // Buendia MRT
      "LTFRB_4974", // Ayala MRT
      "LTFRB_4975", // Magellanes MRT
      "LTFRB_4976"  // Taft Ave MRT
    ),
    List(
      RailFare(0),
      RailFare(10),
      RailFare(10),
      RailFare(11),
      RailFare(11),
      RailFare(12),
      RailFare(12),
      RailFare(12),
      RailFare(14),
      RailFare(14),
      RailFare(14),
      RailFare(15),
      RailFare(15)
    )
  )

  val pnr = new RailLine(List(), List()) {
    override def getFare(from: String, to: String) = RailFare(0)
  }

  def get(routeId: String, from: String, to: String) = {
    val line = routeId match {
      case "ROUTE_880747" if lrt1.has(from) && lrt1.has(to) => lrt1
      case "ROUTE_880747" => lrt1Extended
      case "ROUTE_880801" => lrt2
      case "ROUTE_880854" => mrt3
      case "ROUTE_880872" => pnr
    }
    line.getFare(from, to)
  }
}

