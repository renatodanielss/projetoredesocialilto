<%

long myPortraitViewportWidth = 0;
long myPortraitViewportHeight = 0;
double myPortraitViewportScale = 1.0;

long myLandscapeViewportWidth = 0;
long myLandscapeViewportHeight = 0;
double myLandscapeViewportScale = 1.0;

// calculations

String viewport = "";
Matcher viewportMatches = Pattern.compile("^(.*)(<meta[^>]*name=\"viewport\"[^>]*>)(.*)$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(htmlhead);
if (viewportMatches.find()) {
	viewport = "" + viewportMatches.group(2);
	Matcher viewportContentMatches = Pattern.compile("^<meta[^>]*name=\"viewport\"[^>]*content=\"([^\"]*)\"[^>]*>$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(viewport);
	if (viewportContentMatches.find()) {
		String viewportcontent = "" + viewportContentMatches.group(1);

		Matcher viewportWidthMatches = Pattern.compile("^(.*)(^|\\s|,| )width=([^,\\s ]+?)(\\s|,|$).*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(viewportcontent);
		if (viewportWidthMatches.find()) {
			String viewportwidth = "" + viewportWidthMatches.group(3);
			if (viewportwidth.equals("device-width")) {
				myPortraitViewportWidth = devicePortraitViewportWidth;
				myPortraitViewportScale = devicePortraitViewportScale;
				myLandscapeViewportWidth = deviceLandscapeViewportWidth;
				myLandscapeViewportScale = deviceLandscapeViewportScale;
			} else {
				// !!!!! numeric width (apart from the same as the "device-width") should not used - does not display properly in the simulators and desktop web browsers
				myPortraitViewportWidth = Common.intnumber(viewportwidth);
				myPortraitViewportScale = devicePortraitViewportScale * ((float)myPortraitViewportWidth / (float)devicePortraitViewportWidth);
				myLandscapeViewportWidth = Common.intnumber(viewportwidth);
				myLandscapeViewportScale = deviceLandscapeViewportScale * ((float)myLandscapeViewportWidth / (float)deviceLandscapeViewportWidth);
			}
		}

		Matcher viewportInitialScaleMatches = Pattern.compile("^(.*)(^|\\s|,| )initial-scale=([^,\\s ]+?)(\\s|,|$).*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(viewportcontent);
		Matcher viewportMinimumScaleMatches = Pattern.compile("^(.*)(^|\\s|,| )minimum-scale=([^,\\s ]+?)(\\s|,|$).*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(viewportcontent);
		if (viewportInitialScaleMatches.find()) {
			String viewportscale = "" + viewportInitialScaleMatches.group(3);
			if (Common.number(viewportscale)>0) {
				myPortraitViewportWidth = devicePortraitViewportWidth;
				myPortraitViewportScale = devicePortraitViewportScale * Common.number(viewportscale) / 1.0;
				myLandscapeViewportWidth = deviceLandscapeViewportWidth;
				myLandscapeViewportScale = deviceLandscapeViewportScale;
			}
		} else if (viewportMinimumScaleMatches.find()) {
			String viewportscale = "" + viewportMinimumScaleMatches.group(3);
			if (Common.number(viewportscale)>0) {
				myPortraitViewportWidth = devicePortraitViewportWidth;
				myPortraitViewportScale = devicePortraitViewportScale * Common.number(viewportscale) / 1.0;
				myLandscapeViewportWidth = deviceLandscapeViewportWidth;
				myLandscapeViewportScale = deviceLandscapeViewportScale;
			}
		}

		viewportPortraitFrameScale = devicePortraitFrameScale * myPortraitViewportScale;
		viewportPortraitFrameWidth = Math.round(devicePortraitFrameWidth / myPortraitViewportScale);
		viewportPortraitFrameHeight = Math.round(devicePortraitFrameHeight / myPortraitViewportScale);

		viewportLandscapeFrameScale = deviceLandscapeFrameScale * myLandscapeViewportScale;
		viewportLandscapeFrameWidth = Math.round(deviceLandscapeFrameWidth / myLandscapeViewportScale);
		viewportLandscapeFrameHeight = Math.round(deviceLandscapeFrameHeight / myLandscapeViewportScale);
	}
}

%>