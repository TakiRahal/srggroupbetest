package com.takirahal.srfgroup.enums;

public enum SourceConnectedDevice {
    WEB_BROWSER{
        @Override
        public String toString() {
            return "WebBrowser";
        }
    },
    MOBILE_BROWSER{
        @Override
        public String toString() {
            return "MobileBrowser";
        }
    },
    GOOGLE_PLUS{
        @Override
        public String toString() {
            return "GooglePlus";
        }
    },
    GOOGLE_ONE_TAP_LOGIN{
        @Override
        public String toString() {
            return "GoogleOneTapLogin";
        }
    },
    FACEBOOK{
        @Override
        public String toString() {
            return "Facebook";
        }
    },
    ANDROID{
        @Override
        public String toString() {
            return "Android";
        }
    },
    IOS{
        @Override
        public String toString() {
            return "iOS";
        }
    }
}
