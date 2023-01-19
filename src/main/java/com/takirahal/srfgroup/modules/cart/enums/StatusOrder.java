package com.takirahal.srfgroup.modules.cart.enums;

public enum StatusOrder {
    PASSED{
        @Override
        public String toString() {
            return "Passed";
        }
    },
    RECEIVED{
        @Override
        public String toString() {
            return "Received";
        }
    },
    FAILURE{
        @Override
        public String toString() {
            return "Failure";
        }
    }
}
