Description:
============

Android application which calculates Maidenhead locator system geocode (AKA QTH
Locator or IARU Locator) from the GPS position. If GPS location is not
available, it should use network location with less precision.


Requirements:
=============

Google Play services, which are currently used for the fused location service.


Limitation:
===========

When the 'Get location' button is clicked, it can show cached location which
shouldn't be older than tens of seconds. This is how the fused location service
from the Google Play services works. If the cached location is old enough
(usually more than a minute or so) it's refreshed. As a workaround opening map
application with the real time position tracking can help to refresh the
cached position more quickly. This may be addressed in the future releases.


Compilation:
============

Use Android Studio.


License:
========

If not stated otherwise the license of the included files is GPLv3+.
Parts of the used graphics were advertised as public domain.

Copyright (C) 2021 Yarda <zbox AT atlas.cz>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Full text of the license is enclosed in COPYING file.
