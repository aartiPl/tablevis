/*******************************************************************************
 * Copyright (c) 2008-2012 by Marcin Kuszczak. All rights reserved.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package net.igsoft.tablevis.marker

data class TypedMarker<T>(override val name: String = "anonymous") : Marker(name)
