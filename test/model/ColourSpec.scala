/**
 * Copyright 2013 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with work for additional information
 * regarding copyright ownership.  The ASF licenses file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use file except in compliance
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
 */

package model

import org.specs2.mutable.Specification
import model.ColourGroup._
import model.Darkness._
import model.Colour._
import argonaut._, Argonaut._, DecodeResult._

/**
 * @author alex
 *
 */
class ColourSpec extends Specification {

  "The number of distinct colours" should {
    "equal 140" in {
      Colour.values.size must be equalTo(140)
      Colour.valueMap.size must be equalTo(140)
    }
  }
  
  /**
   * Test that the colour group and darkness traits correctly set properties in a colour object.
   */
  "Red" should {
    "be reddish" in {
      RED.colourGroup must be equalTo(REDS)
    }
    "be dark" in {
      RED.darkness must be equalTo(DARK)
    }
  }

  "Pink" should {
    "be pinky" in {
      PINK.colourGroup must be equalTo(PINKS)
    }
    "be light" in {
      PINK.darkness must be equalTo(LIGHT)
    }
  }

  "Orange" should {
    "be orangy" in {
      ORANGE.colourGroup must be equalTo(ORANGES)
    }
    "be light" in {
      ORANGE.darkness must be equalTo(LIGHT)
    }
  }

  "Yellow" should {
    "be yellowy" in {
      YELLOW.colourGroup must be equalTo(YELLOWS)
    }
    "be light" in {
      YELLOW.darkness must be equalTo(LIGHT)
    }
  }

  "Purple" should {
    "be purpley" in {
      PURPLE.colourGroup must be equalTo(PURPLES)
    }
    "be dark" in {
      PURPLE.darkness must be equalTo(DARK)
    }
  }

  "Green" should {
    "be reddish" in {
      GREEN.colourGroup must be equalTo(GREENS)
    }
    "be dark" in {
      GREEN.darkness must be equalTo(DARK)
    }
  }

  "Blue" should {
    "be bluey" in {
      BLUE.colourGroup must be equalTo(BLUES)
    }
    "be dark" in {
      BLUE.darkness must be equalTo(DARK)
    }
  }
  
  "Brown" should {
    "be browny" in {
      BROWN.colourGroup must be equalTo(BROWNS)
    }
    "be dark" in {
      BROWN.darkness must be equalTo(DARK)
    }
  }

  "White" should {
    "be whitish" in {
      WHITE.colourGroup must be equalTo(WHITES)
    }
    "be light" in {
      WHITE.darkness must be equalTo(LIGHT)
    }
  }

  "Grey" should {
    "be grey" in {
      GRAY.colourGroup must be equalTo(GREYS)
    }
    "be dark" in {
      GRAY.darkness must be equalTo(DARK)
    }
  }

  "A dark colour" should {
    "serialise with 'dark' as true" in {
      GRAY.asJson.toString must be equalTo(
        """{"rgb":"#808080","name":["gray"],"dark":true,"token":"GRAY","htmlName":"Gray","group":"Greys"}""")
    }
  }

  "A light colour" should {
    "serialise with 'dark' as false" in {
      WHITE.asJson.toString must be equalTo(
        """{"rgb":"#FFFFFF","name":["white"],"dark":false,"token":"WHITE","htmlName":"White","group":"Whites"}""")
    }
  }
}