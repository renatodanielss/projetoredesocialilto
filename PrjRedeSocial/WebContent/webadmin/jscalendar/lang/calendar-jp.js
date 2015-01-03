// ** I18N

// Calendar EN language
// Encoding: any
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Calendar._DN = new Array
("\u65E5",
 "\u6708",
 "\u706B",
 "\u6C34",
 "\u6728",
 "\u91D1",
 "\u571F",
 "\u65E5");

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Calendar._SDN_len = N; // short day name length
//   Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
Calendar._SDN = new Array
("\u65E5",
 "\u6708",
 "\u706B",
 "\u6C34",
 "\u6728",
 "\u91D1",
 "\u571F",
 "\u65E5");

// First day of the week. "0" means display Sunday first, "1" means display
// Monday first, etc.
Calendar._FD = 0;

// full month names
Calendar._MN = new Array
("1\u6708",
 "2\u6708",
 "3\u6708",
 "4\u6708",
 "5\u6708",
 "6\u6708",
 "7\u6708",
 "8\u6708",
 "9\u6708",
 "10\u6708",
 "11\u6708",
 "12\u6708");

// short month names
Calendar._SMN = new Array
("1\u6708",
 "2\u6708",
 "3\u6708",
 "4\u6708",
 "5\u6708",
 "6\u6708",
 "7\u6708",
 "8\u6708",
 "9\u6708",
 "10\u6708",
 "11\u6708",
 "12\u6708");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "\u30AB\u30EC\u30F3\u30C0\u30FC\u306B\u3064\u3044\u3066";

Calendar._TT["ABOUT"] =
"DHTML Date/Time \u30BB\u30AF\u30B7\u30E7\u30F3\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"For latest version visit: http://www.dynarch.com/projects/calendar/\n" +
"Distributed under GNU LGPL.  See http://gnu.org/licenses/lgpl.html for details." +
"\n\n" +
"Date selection:\n" +
"- Use the \xab, \xbb buttons to select year\n" +
"- Use the " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " buttons to select month\n" +
"- Hold mouse button on any of the above buttons for faster selection.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"Time selection:\n" +
"- Click on any of the time parts to increase it\n" +
"- or Shift-click to decrease it\n" +
"- or click and drag for faster selection.";

Calendar._TT["PREV_YEAR"] = "\u524D\u5E74";
Calendar._TT["PREV_MONTH"] = "\u524D\u6708";
Calendar._TT["GO_TODAY"] = "\u4ECA\u65E5";
Calendar._TT["NEXT_MONTH"] = "\u7FCC\u6708";
Calendar._TT["NEXT_YEAR"] = "\u7FCC\u5E74";
Calendar._TT["SEL_DATE"] = "\u65E5\u4ED8\u9078\u629E";
Calendar._TT["DRAG_TO_MOVE"] = "\u30A6\u30A3\u30F3\u30C9\u30A6\u306E\u79FB\u52D5";
Calendar._TT["PART_TODAY"] = " (\u4ECA\u65E5)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "%s\u3092\u6700\u521D\u306B";
Calendar._TT["MON_FIRST"] = "\u6708\u66DC\u65E5\u3092\u5148\u982D\u306B";
Calendar._TT["SUN_FIRST"] = "\u65E5\u66DC\u65E5\u3092\u5148\u982D\u306B";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "\u9589\u3058\u308B";
Calendar._TT["TODAY"] = "\u4ECA\u65E5";
Calendar._TT["TIME_PART"] = "(Shift-)\u30AF\u30EA\u30C3\u30AF\u30C9\u30E9\u30C3\u30B0\u3067\u5024\u3092\u5909\u66F4";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "y-mm-dd";
Calendar._TT["TT_DATE_FORMAT"] = "%m\u6708 %d\u65E5 (%a)";

Calendar._TT["WK"] = "\u9031";
Calendar._TT["TIME"] = "\u6642\u9593:";
