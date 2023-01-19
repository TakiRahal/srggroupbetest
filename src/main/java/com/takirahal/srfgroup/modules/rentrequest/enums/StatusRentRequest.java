package com.takirahal.srfgroup.modules.rentrequest.enums;

public enum StatusRentRequest {
    STANDBY{
        @Override
        public String toString() {
            return "StandBy";
        }
    },
    ACCEPTED{
        @Override
        public String toString() {
            return "Accepted";
        }
    },
    REFUSED{
        @Override
        public String toString() {
            return "Refused";
        }
    }
}
