package com.takirahal.srfgroup.modules.cart.enums;

public enum StatusCart {
    STANDBY{
        @Override
        public String toString() {
            return "StandBy";
        }
    },
    PASSED{
        @Override
        public String toString() {
            return "Passed";
        }
    }
}
