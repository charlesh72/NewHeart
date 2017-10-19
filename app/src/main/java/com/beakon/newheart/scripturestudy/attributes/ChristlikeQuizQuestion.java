/*
 * Copyright (C) 2017 Charles Hancock
 *
 * NewHeart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * NewHeart is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.beakon.newheart.scripturestudy.attributes;

import com.beakon.newheart.R;

/**
 * Created by Charles on 10/18/2017.
 */

public class ChristlikeQuizQuestion {

    public static final int ATTR_FAITH = 0;
    public static final int ATTR_HOPE = 1;
    public static final int ATTR_CHARITY_LOVE = 2;
    public static final int ATTR_VIRTUE = 3;
    public static final int ATTR_KNOWLEDGE = 4;
    public static final int ATTR_PATIENCE = 5;
    public static final int ATTR_HUMILITY = 6;
    public static final int ATTR_DILIGENCE = 7;
    public static final int ATTR_OBEDIENCE = 8;

    public String question;

    public int attributeCategory;

    public ChristlikeQuizQuestion(String question, int attributeCategory) {
        this.question = question;
        this.attributeCategory = attributeCategory;
    }
}