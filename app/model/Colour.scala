/**
 * Copyright 2013 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * @author unclealex72
 *
 */
package model;

import scala.collection.immutable.Seq
import argonaut._, Argonaut._, DecodeResult._

/**
 * A grouping of colours, roughly by how close they are to a well known colour.
 */
sealed class ColourGroup(
  /**
   * The token used to persist this group in a database.
   */
  val persistableToken: String,
  /**
   * A human readable description of this grouping.
   */
  val description: String) extends ColourGroup.Value {

  override def toString = s"ColourGroup($persistableToken)"

}

object ColourGroup extends PersistableEnumeration[ColourGroup] {

  /**
   * Red-like colours.
   */
  case object REDS extends ColourGroup("REDS", "Reds")
  REDS
  trait RedLike { val colourGroup = REDS }

  /**
   * Pink-like colours.
   */
  case object PINKS extends ColourGroup("PINKS", "Pinks")
  PINKS
  trait PinkLike { val colourGroup = PINKS }

  /**
   * Orange-like colours.
   */
  case object ORANGES extends ColourGroup("ORANGES", "Oranges")
  ORANGES
  trait OrangeLike { val colourGroup = ORANGES }

  /**
   * Yellow-like colours.
   */
  case object YELLOWS extends ColourGroup("YELLOWS", "Yellows")
  YELLOWS
  trait YellowLike { val colourGroup = YELLOWS }

  /**
   * Purple-like colours.
   */
  case object PURPLES extends ColourGroup("PURPLES", "Purples")
  PURPLES
  trait PurpleLike { val colourGroup = PURPLES }

  /**
   * Green-like colours.
   */
  case object GREENS extends ColourGroup("GREENS", "Greens")
  GREENS
  trait GreenLike { val colourGroup = GREENS }

  /**
   * Blue-like colours.
   */
  case object BLUES extends ColourGroup("BLUES", "Blues")
  BLUES
  trait BlueLike { val colourGroup = BLUES }

  /**
   * Green-like colours.
   */
  case object BROWNS extends ColourGroup("BROWNS", "Browns")
  BROWNS
  trait BrownLike { val colourGroup = BROWNS }

  /**
   * White-like colours.
   */
  case object WHITES extends ColourGroup("WHITES", "Whites")
  WHITES
  trait WhiteLike { val colourGroup = WHITES }

  /**
   * Green-like colours.
   */
  case object GREYS extends ColourGroup("GREYS", "Greys")
  GREYS
  trait GreyLike { val colourGroup = GREYS }

}

/**
 * A description of the darkness of a colur.
 */
sealed class Darkness(
  /**
   * The token used to store values in a database.
   */
  val persistableToken: String,
  /**
   * True if this colour is dark, false otherwise.
   */
  val isDark: Boolean) extends Darkness.Value {

  override def toString = s"Dark($persistableToken)"

}

object Darkness extends PersistableEnumeration[Darkness] {

  /**
   * Dark colours
   */
  case object DARK extends Darkness("DARK", true)
  DARK
  trait Dark { val darkness = DARK }

  /**
   * Light colours
   */
  case object LIGHT extends Darkness("DARK", false)
  DARK
  trait Light { val darkness = LIGHT }

}

/**
 * An enumeration of named HTML colours.
 */
sealed abstract class Colour(
  /**
   * The token used to persist this object in a database.
   */
  val persistableToken: String,
  /**
   * The html name of this colour.
   */
  val htmlName: String,
  /**
   * The RGB value of this colour.
   */
  val rgb: String,
  /**
   * The sequence of words used to describe this string.
   */
  val name: String*) extends Colour.Value {

  /**
   * The colour group to which this colour belongs.
   */
  val colourGroup: ColourGroup
  /**
   * The darkness of this colour.
   */
  val darkness: Darkness

  override def toString = s"Colour($persistableToken)"
}

object Colour extends PersistableEnumeration[Colour] {

  import Darkness._
  import ColourGroup._

  object INDIAN_RED extends Colour("INDIAN_RED", "IndianRed", "#CD5C5C", "indian", "red") with RedLike with Dark; INDIAN_RED
  object LIGHT_CORAL extends Colour("LIGHT_CORAL", "LightCoral", "#F08080", "light", "coral") with RedLike with Light; LIGHT_CORAL
  object SALMON extends Colour("SALMON", "Salmon", "#FA8072", "salmon") with RedLike with Light; SALMON
  object DARK_SALMON extends Colour("DARK_SALMON", "DarkSalmon", "#E9967A", "dark", "salmon") with RedLike with Light; DARK_SALMON
  object RED extends Colour("RED", "Red", "#FF0000", "red") with RedLike with Dark; RED
  object CRIMSON extends Colour("CRIMSON", "Crimson", "#DC143C", "crimson") with RedLike with Dark; CRIMSON
  object FIRE_BRICK extends Colour("FIRE_BRICK", "FireBrick", "#B22222", "fire", "brick") with RedLike with Dark; FIRE_BRICK
  object DARK_RED extends Colour("DARK_RED", "DarkRed", "#8B0000", "dark", "red") with RedLike with Dark; DARK_RED
  object PINK extends Colour("PINK", "Pink", "#FFC0CB", "pink") with PinkLike with Light; PINK
  object LIGHT_PINK extends Colour("LIGHT_PINK", "LightPink", "#FFB6C1", "light", "pink") with PinkLike with Light; LIGHT_PINK
  object HOT_PINK extends Colour("HOT_PINK", "HotPink", "#FF69B4", "hot", "pink") with PinkLike with Light; HOT_PINK
  object DEEP_PINK extends Colour("DEEP_PINK", "DeepPink", "#FF1493", "deep", "pink") with PinkLike with Light; DEEP_PINK
  object MEDIUM_VIOLET_RED extends Colour("MEDIUM_VIOLET_RED", "MediumVioletRed", "#C71585", "medium", "violet", "red") with PinkLike with Dark; MEDIUM_VIOLET_RED
  object PALE_VIOLET_RED extends Colour("PALE_VIOLET_RED", "PaleVioletRed", "#DB7093", "pale", "violet", "red") with PinkLike with Light; PALE_VIOLET_RED
  object LIGHT_SALMON extends Colour("LIGHT_SALMON", "LightSalmon", "#FFA07A", "light", "salmon") with OrangeLike with Light; LIGHT_SALMON
  object CORAL extends Colour("CORAL", "Coral", "#FF7F50", "coral") with OrangeLike with Light; CORAL
  object TOMATO extends Colour("TOMATO", "Tomato", "#FF6347", "tomato") with OrangeLike with Light; TOMATO
  object ORANGE_RED extends Colour("ORANGE_RED", "OrangeRed", "#FF4500", "orange", "red") with OrangeLike with Light; ORANGE_RED
  object DARK_ORANGE extends Colour("DARK_ORANGE", "DarkOrange", "#FF8C00", "dark", "orange") with OrangeLike with Light; DARK_ORANGE
  object ORANGE extends Colour("ORANGE", "Orange", "#FFA500", "orange") with OrangeLike with Light; ORANGE
  object GOLD extends Colour("GOLD", "Gold", "#FFD700", "gold") with YellowLike with Light; GOLD
  object YELLOW extends Colour("YELLOW", "Yellow", "#FFFF00", "yellow") with YellowLike with Light; YELLOW
  object LIGHT_YELLOW extends Colour("LIGHT_YELLOW", "LightYellow", "#FFFFE0", "light", "yellow") with YellowLike with Light; LIGHT_YELLOW
  object LEMON_CHIFFON extends Colour("LEMON_CHIFFON", "LemonChiffon", "#FFFACD", "lemon", "chiffon") with YellowLike with Light; LEMON_CHIFFON
  object LIGHT_GOLDENROD_YELLOW extends Colour("LIGHT_GOLDENROD_YELLOW", "LightGoldenrodYellow", "#FAFAD2", "light", "goldenrod", "yellow") with YellowLike with Light; LIGHT_GOLDENROD_YELLOW
  object PAPAYA_WHIP extends Colour("PAPAYA_WHIP", "PapayaWhip", "#FFEFD5", "papaya", "whip") with YellowLike with Light; PAPAYA_WHIP
  object MOCCASIN extends Colour("MOCCASIN", "Moccasin", "#FFE4B5", "moccasin") with YellowLike with Light; MOCCASIN
  object PEACH_PUFF extends Colour("PEACH_PUFF", "PeachPuff", "#FFDAB9", "peach", "puff") with YellowLike with Light; PEACH_PUFF
  object PALE_GOLDENROD extends Colour("PALE_GOLDENROD", "PaleGoldenrod", "#EEE8AA", "pale", "goldenrod") with YellowLike with Light; PALE_GOLDENROD
  object KHAKI extends Colour("KHAKI", "Khaki", "#F0E68C", "khaki") with YellowLike with Light; KHAKI
  object DARK_KHAKI extends Colour("DARK_KHAKI", "DarkKhaki", "#BDB76B", "dark", "khaki") with YellowLike with Light; DARK_KHAKI
  object LAVENDER extends Colour("LAVENDER", "Lavender", "#E6E6FA", "lavender") with PurpleLike with Light; LAVENDER
  object THISTLE extends Colour("THISTLE", "Thistle", "#D8BFD8", "thistle") with PurpleLike with Light; THISTLE
  object PLUM extends Colour("PLUM", "Plum", "#DDA0DD", "plum") with PurpleLike with Light; PLUM
  object VIOLET extends Colour("VIOLET", "Violet", "#EE82EE", "violet") with PurpleLike with Light; VIOLET
  object ORCHID extends Colour("ORCHID", "Orchid", "#DA70D6", "orchid") with PurpleLike with Light; ORCHID
  object FUCHSIA extends Colour("FUCHSIA", "Fuchsia", "#FF00FF", "fuchsia") with PurpleLike with Light; FUCHSIA
  object MAGENTA extends Colour("MAGENTA", "Magenta", "#FF00FF", "magenta") with PurpleLike with Light; MAGENTA
  object MEDIUM_ORCHID extends Colour("MEDIUM_ORCHID", "MediumOrchid", "#BA55D3", "medium", "orchid") with PurpleLike with Dark; MEDIUM_ORCHID
  object MEDIUM_PURPLE extends Colour("MEDIUM_PURPLE", "MediumPurple", "#9370DB", "medium", "purple") with PurpleLike with Light; MEDIUM_PURPLE
  object BLUE_VIOLET extends Colour("BLUE_VIOLET", "BlueViolet", "#8A2BE2", "blue", "violet") with PurpleLike with Dark; BLUE_VIOLET
  object DARK_VIOLET extends Colour("DARK_VIOLET", "DarkViolet", "#9400D3", "dark", "violet") with PurpleLike with Dark; DARK_VIOLET
  object DARK_ORCHID extends Colour("DARK_ORCHID", "DarkOrchid", "#9932CC", "dark", "orchid") with PurpleLike with Dark; DARK_ORCHID
  object DARK_MAGENTA extends Colour("DARK_MAGENTA", "DarkMagenta", "#8B008B", "dark", "magenta") with PurpleLike with Dark; DARK_MAGENTA
  object PURPLE extends Colour("PURPLE", "Purple", "#800080", "purple") with PurpleLike with Dark; PURPLE
  object INDIGO extends Colour("INDIGO", "Indigo", "#4B0082", "indigo") with PurpleLike with Dark; INDIGO
  object DARK_SLATE_BLUE extends Colour("DARK_SLATE_BLUE", "DarkSlateBlue", "#483D8B", "dark", "slate", "blue") with PurpleLike with Dark; DARK_SLATE_BLUE
  object SLATE_BLUE extends Colour("SLATE_BLUE", "SlateBlue", "#6A5ACD", "slate", "blue") with PurpleLike with Dark; SLATE_BLUE
  object MEDIUM_SLATE_BLUE extends Colour("MEDIUM_SLATE_BLUE", "MediumSlateBlue", "#7B68EE", "medium", "slate", "blue") with PurpleLike with Dark; MEDIUM_SLATE_BLUE
  object GREEN_YELLOW extends Colour("GREEN_YELLOW", "GreenYellow", "#ADFF2F", "green", "yellow") with GreenLike with Light; GREEN_YELLOW
  object CHARTREUSE extends Colour("CHARTREUSE", "Chartreuse", "#7FFF00", "chartreuse") with GreenLike with Light; CHARTREUSE
  object LAWN_GREEN extends Colour("LAWN_GREEN", "LawnGreen", "#7CFC00", "lawn", "green") with GreenLike with Light; LAWN_GREEN
  object LIME extends Colour("LIME", "Lime", "#00FF00", "lime") with GreenLike with Light; LIME
  object LIME_GREEN extends Colour("LIME_GREEN", "LimeGreen", "#32CD32", "lime", "green") with GreenLike with Light; LIME_GREEN
  object PALE_GREEN extends Colour("PALE_GREEN", "PaleGreen", "#98FB98", "pale", "green") with GreenLike with Light; PALE_GREEN
  object LIGHT_GREEN extends Colour("LIGHT_GREEN", "LightGreen", "#90EE90", "light", "green") with GreenLike with Light; LIGHT_GREEN
  object MEDIUM_SPRING_GREEN extends Colour("MEDIUM_SPRING_GREEN", "MediumSpringGreen", "#00FA9A", "medium", "spring", "green") with GreenLike with Light; MEDIUM_SPRING_GREEN
  object SPRING_GREEN extends Colour("SPRING_GREEN", "SpringGreen", "#00FF7F", "spring", "green") with GreenLike with Light; SPRING_GREEN
  object MEDIUM_SEA_GREEN extends Colour("MEDIUM_SEA_GREEN", "MediumSeaGreen", "#3CB371", "medium", "sea", "green") with GreenLike with Light; MEDIUM_SEA_GREEN
  object SEA_GREEN extends Colour("SEA_GREEN", "SeaGreen", "#2E8B57", "sea", "green") with GreenLike with Dark; SEA_GREEN
  object FOREST_GREEN extends Colour("FOREST_GREEN", "ForestGreen", "#228B22", "forest", "green") with GreenLike with Dark; FOREST_GREEN
  object GREEN extends Colour("GREEN", "Green", "#008000", "green") with GreenLike with Dark; GREEN
  object DARK_GREEN extends Colour("DARK_GREEN", "DarkGreen", "#006400", "dark", "green") with GreenLike with Dark; DARK_GREEN
  object YELLOW_GREEN extends Colour("YELLOW_GREEN", "YellowGreen", "#9ACD32", "yellow", "green") with GreenLike with Light; YELLOW_GREEN
  object OLIVE_DRAB extends Colour("OLIVE_DRAB", "OliveDrab", "#6B8E23", "olive", "drab") with GreenLike with Dark; OLIVE_DRAB
  object OLIVE extends Colour("OLIVE", "Olive", "#808000", "olive") with GreenLike with Dark; OLIVE
  object DARK_OLIVE_GREEN extends Colour("DARK_OLIVE_GREEN", "DarkOliveGreen", "#556B2F", "dark", "olive", "green") with GreenLike with Dark; DARK_OLIVE_GREEN
  object MEDIUM_AQUAMARINE extends Colour("MEDIUM_AQUAMARINE", "MediumAquamarine", "#66CDAA", "medium", "aquamarine") with GreenLike with Light; MEDIUM_AQUAMARINE
  object DARK_SEA_GREEN extends Colour("DARK_SEA_GREEN", "DarkSeaGreen", "#8FBC8F", "dark", "sea", "green") with GreenLike with Light; DARK_SEA_GREEN
  object LIGHT_SEA_GREEN extends Colour("LIGHT_SEA_GREEN", "LightSeaGreen", "#20B2AA", "light", "sea", "green") with GreenLike with Light; LIGHT_SEA_GREEN
  object DARK_CYAN extends Colour("DARK_CYAN", "DarkCyan", "#008B8B", "dark", "cyan") with GreenLike with Dark; DARK_CYAN
  object TEAL extends Colour("TEAL", "Teal", "#008080", "teal") with GreenLike with Dark; TEAL
  object AQUA extends Colour("AQUA", "Aqua", "#00FFFF", "aqua") with BlueLike with Light; AQUA
  object CYAN extends Colour("CYAN", "Cyan", "#00FFFF", "cyan") with BlueLike with Light; CYAN
  object LIGHT_CYAN extends Colour("LIGHT_CYAN", "LightCyan", "#E0FFFF", "light", "cyan") with BlueLike with Light; LIGHT_CYAN
  object PALE_TURQUOISE extends Colour("PALE_TURQUOISE", "PaleTurquoise", "#AFEEEE", "pale", "turquoise") with BlueLike with Light; PALE_TURQUOISE
  object AQUAMARINE extends Colour("AQUAMARINE", "Aquamarine", "#7FFFD4", "aquamarine") with BlueLike with Light; AQUAMARINE
  object TURQUOISE extends Colour("TURQUOISE", "Turquoise", "#40E0D0", "turquoise") with BlueLike with Light; TURQUOISE
  object MEDIUM_TURQUOISE extends Colour("MEDIUM_TURQUOISE", "MediumTurquoise", "#48D1CC", "medium", "turquoise") with BlueLike with Light; MEDIUM_TURQUOISE
  object DARK_TURQUOISE extends Colour("DARK_TURQUOISE", "DarkTurquoise", "#00CED1", "dark", "turquoise") with BlueLike with Light; DARK_TURQUOISE
  object CADET_BLUE extends Colour("CADET_BLUE", "CadetBlue", "#5F9EA0", "cadet", "blue") with BlueLike with Light; CADET_BLUE
  object STEEL_BLUE extends Colour("STEEL_BLUE", "SteelBlue", "#4682B4", "steel", "blue") with BlueLike with Dark; STEEL_BLUE
  object LIGHT_STEEL_BLUE extends Colour("LIGHT_STEEL_BLUE", "LightSteelBlue", "#B0C4DE", "light", "steel", "blue") with BlueLike with Light; LIGHT_STEEL_BLUE
  object POWDER_BLUE extends Colour("POWDER_BLUE", "PowderBlue", "#B0E0E6", "powder", "blue") with BlueLike with Light; POWDER_BLUE
  object LIGHT_BLUE extends Colour("LIGHT_BLUE", "LightBlue", "#ADD8E6", "light", "blue") with BlueLike with Light; LIGHT_BLUE
  object SKY_BLUE extends Colour("SKY_BLUE", "SkyBlue", "#87CEEB", "sky", "blue") with BlueLike with Light; SKY_BLUE
  object LIGHT_SKY_BLUE extends Colour("LIGHT_SKY_BLUE", "LightSkyBlue", "#87CEFA", "light", "sky", "blue") with BlueLike with Light; LIGHT_SKY_BLUE
  object DEEP_SKY_BLUE extends Colour("DEEP_SKY_BLUE", "DeepSkyBlue", "#00BFFF", "deep", "sky", "blue") with BlueLike with Light; DEEP_SKY_BLUE
  object DODGER_BLUE extends Colour("DODGER_BLUE", "DodgerBlue", "#1E90FF", "dodger", "blue") with BlueLike with Light; DODGER_BLUE
  object CORNFLOWER_BLUE extends Colour("CORNFLOWER_BLUE", "CornflowerBlue", "#6495ED", "cornflower", "blue") with BlueLike with Light; CORNFLOWER_BLUE
  object ROYAL_BLUE extends Colour("ROYAL_BLUE", "RoyalBlue", "#4169E1", "royal", "blue") with BlueLike with Dark; ROYAL_BLUE
  object BLUE extends Colour("BLUE", "Blue", "#0000FF", "blue") with BlueLike with Dark; BLUE
  object MEDIUM_BLUE extends Colour("MEDIUM_BLUE", "MediumBlue", "#0000CD", "medium", "blue") with BlueLike with Dark; MEDIUM_BLUE
  object DARK_BLUE extends Colour("DARK_BLUE", "DarkBlue", "#00008B", "dark", "blue") with BlueLike with Dark; DARK_BLUE
  object NAVY extends Colour("NAVY", "Navy", "#000080", "navy") with BlueLike with Dark; NAVY
  object MIDNIGHT_BLUE extends Colour("MIDNIGHT_BLUE", "MidnightBlue", "#191970", "midnight", "blue") with BlueLike with Dark; MIDNIGHT_BLUE
  object CORNSILK extends Colour("CORNSILK", "Cornsilk", "#FFF8DC", "cornsilk") with BrownLike with Light; CORNSILK
  object BLANCHED_ALMOND extends Colour("BLANCHED_ALMOND", "BlanchedAlmond", "#FFEBCD", "blanched", "almond") with BrownLike with Light; BLANCHED_ALMOND
  object BISQUE extends Colour("BISQUE", "Bisque", "#FFE4C4", "bisque") with BrownLike with Light; BISQUE
  object NAVAJO_WHITE extends Colour("NAVAJO_WHITE", "NavajoWhite", "#FFDEAD", "navajo", "white") with BrownLike with Light; NAVAJO_WHITE
  object WHEAT extends Colour("WHEAT", "Wheat", "#F5DEB3", "wheat") with BrownLike with Light; WHEAT
  object BURLY_WOOD extends Colour("BURLY_WOOD", "BurlyWood", "#DEB887", "burly", "wood") with BrownLike with Light; BURLY_WOOD
  object TAN extends Colour("TAN", "Tan", "#D2B48C", "tan") with BrownLike with Light; TAN
  object ROSY_BROWN extends Colour("ROSY_BROWN", "RosyBrown", "#BC8F8F", "rosy", "brown") with BrownLike with Light; ROSY_BROWN
  object SANDY_BROWN extends Colour("SANDY_BROWN", "SandyBrown", "#F4A460", "sandy", "brown") with BrownLike with Light; SANDY_BROWN
  object GOLDENROD extends Colour("GOLDENROD", "Goldenrod", "#DAA520", "goldenrod") with BrownLike with Light; GOLDENROD
  object DARK_GOLDENROD extends Colour("DARK_GOLDENROD", "DarkGoldenrod", "#B8860B", "dark", "goldenrod") with BrownLike with Light; DARK_GOLDENROD
  object PERU extends Colour("PERU", "Peru", "#CD853F", "peru") with BrownLike with Light; PERU
  object CHOCOLATE extends Colour("CHOCOLATE", "Chocolate", "#D2691E", "chocolate") with BrownLike with Light; CHOCOLATE
  object SADDLE_BROWN extends Colour("SADDLE_BROWN", "SaddleBrown", "#8B4513", "saddle", "brown") with BrownLike with Dark; SADDLE_BROWN
  object SIENNA extends Colour("SIENNA", "Sienna", "#A0522D", "sienna") with BrownLike with Dark; SIENNA
  object BROWN extends Colour("BROWN", "Brown", "#A52A2A", "brown") with BrownLike with Dark; BROWN
  object MAROON extends Colour("MAROON", "Maroon", "#800000", "maroon") with BrownLike with Dark; MAROON
  object WHITE extends Colour("WHITE", "White", "#FFFFFF", "white") with WhiteLike with Light; WHITE
  object SNOW extends Colour("SNOW", "Snow", "#FFFAFA", "snow") with WhiteLike with Light; SNOW
  object HONEYDEW extends Colour("HONEYDEW", "Honeydew", "#F0FFF0", "honeydew") with WhiteLike with Light; HONEYDEW
  object MINT_CREAM extends Colour("MINT_CREAM", "MintCream", "#F5FFFA", "mint", "cream") with WhiteLike with Light; MINT_CREAM
  object AZURE extends Colour("AZURE", "Azure", "#F0FFFF", "azure") with WhiteLike with Light; AZURE
  object ALICE_BLUE extends Colour("ALICE_BLUE", "AliceBlue", "#F0F8FF", "alice", "blue") with WhiteLike with Light; ALICE_BLUE
  object GHOST_WHITE extends Colour("GHOST_WHITE", "GhostWhite", "#F8F8FF", "ghost", "white") with WhiteLike with Light; GHOST_WHITE
  object WHITE_SMOKE extends Colour("WHITE_SMOKE", "WhiteSmoke", "#F5F5F5", "white", "smoke") with WhiteLike with Light; WHITE_SMOKE
  object SEASHELL extends Colour("SEASHELL", "Seashell", "#FFF5EE", "seashell") with WhiteLike with Light; SEASHELL
  object BEIGE extends Colour("BEIGE", "Beige", "#F5F5DC", "beige") with WhiteLike with Light; BEIGE
  object OLD_LACE extends Colour("OLD_LACE", "OldLace", "#FDF5E6", "old", "lace") with WhiteLike with Light; OLD_LACE
  object FLORAL_WHITE extends Colour("FLORAL_WHITE", "FloralWhite", "#FFFAF0", "floral", "white") with WhiteLike with Light; FLORAL_WHITE
  object IVORY extends Colour("IVORY", "Ivory", "#FFFFF0", "ivory") with WhiteLike with Light; IVORY
  object ANTIQUE_WHITE extends Colour("ANTIQUE_WHITE", "AntiqueWhite", "#FAEBD7", "antique", "white") with WhiteLike with Light; ANTIQUE_WHITE
  object LINEN extends Colour("LINEN", "Linen", "#FAF0E6", "linen") with WhiteLike with Light; LINEN
  object LAVENDER_BLUSH extends Colour("LAVENDER_BLUSH", "LavenderBlush", "#FFF0F5", "lavender", "blush") with WhiteLike with Light; LAVENDER_BLUSH
  object MISTY_ROSE extends Colour("MISTY_ROSE", "MistyRose", "#FFE4E1", "misty", "rose") with WhiteLike with Light; MISTY_ROSE
  object GAINSBORO extends Colour("GAINSBORO", "Gainsboro", "#DCDCDC", "gainsboro") with GreyLike with Light; GAINSBORO
  object LIGHT_GREY extends Colour("LIGHT_GREY", "LightGrey", "#D3D3D3", "light", "grey") with GreyLike with Light; LIGHT_GREY
  object SILVER extends Colour("SILVER", "Silver", "#C0C0C0", "silver") with GreyLike with Light; SILVER
  object DARK_GRAY extends Colour("DARK_GRAY", "DarkGray", "#A9A9A9", "dark", "gray") with GreyLike with Light; DARK_GRAY
  object GRAY extends Colour("GRAY", "Gray", "#808080", "gray") with GreyLike with Dark; GRAY
  object DIM_GRAY extends Colour("DIM_GRAY", "DimGray", "#696969", "dim", "gray") with GreyLike with Dark; DIM_GRAY
  object LIGHT_SLATE_GRAY extends Colour("LIGHT_SLATE_GRAY", "LightSlateGray", "#778899", "light", "slate", "gray") with GreyLike with Light; LIGHT_SLATE_GRAY
  object SLATE_GRAY extends Colour("SLATE_GRAY", "SlateGray", "#708090", "slate", "gray") with GreyLike with Dark; SLATE_GRAY
  object DARK_SLATE_GRAY extends Colour("DARK_SLATE_GRAY", "DarkSlateGray", "#2F4F4F", "dark", "slate", "gray") with GreyLike with Dark; DARK_SLATE_GRAY
  object BLACK extends Colour("BLACK", "Black", "#000000", "black") with GreyLike with Dark; BLACK
  
  /**
   * JSON serialisation.
   */
  implicit val colourEncodeJson: EncodeJson[Colour] =
    EncodeJson((c: Colour) =>
      ("dark" := c.darkness == DARK) ->: 
      ("group" := c.colourGroup.description) ->: 
      ("name" := c.name.toList) ->:
      ("htmlName" := c.htmlName) ->:
      ("rgb" := c.rgb) ->:
      ("token" := c.persistableToken) ->: jEmptyObject)
}
