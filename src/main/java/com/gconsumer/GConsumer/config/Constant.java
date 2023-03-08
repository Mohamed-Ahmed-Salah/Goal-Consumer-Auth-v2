package com.gconsumer.GConsumer.config;

public class Constant {

    public static class ResponseCode {
        public static class Success {
            public static final int code = 0;
            public static final String msg = "Success";
        }

        public static class Validation {
            public static final int code = 400;
            public static final String msg = "Validation Error";
        }

        //user management error code start with -100
        public static class UserNotFound {
            public static final int code = -100;
            public static final String msg = "User is not Found";
        }

        public static class WrongUsernameOrPassword {
            public static final int code = -104;
            public static final String msg = "Username or password is incorrect";
        }

        public static class UserIsLocked {
            public static final int code = -105;
            public static final String msg = "User is Locked";
        }

        public static class UserOrOtpNotFound {
            public static final int code = -101;
            public static final String msg = "Otp is either incorrect or not Found";
        }

        public static class ExpiredOtp {
            public static final int code = -102;
            public static final String msg = "Otp Expired";
        }

        public static class OtpNotFound {
            public static final int code = -103;
            public static final String msg = "Otp not found";
        }


        public static class RoleOperationNotFound {
            public static final int code = -140;
            public static final String msg = "Role-Operation with ID Not Found";
        }
        public static class RoleOperationAlreadyExist {
            public static final int code = -141;
            public static final String msg = "Operation already exists";
        }

        public static class PermissionNotFound {
            public static final int code = -160;
            public static final String msg = "Permission with ID Not Found";
        }

        public static class PermissionOrUserNotFound {
            public static final int code = -161;
            public static final String msg = "Permission Or User Not Found";
        }
        public static class PermissionAlreadyExist {
            public static final int code = -162;
            public static final String msg = "Permission already exists";
        }

        public static class OperationNotFound {
            public static final int code = -170;
            public static final String msg = "Operation with ID Not Found";
        }
        public static class OperationAlreadyExist {
            public static final int code = -171;
            public static final String msg = "Operation already exists";
        }
        public static class ApplicationAlreadyExist {
            public static final int code = -180;
            public static final String msg = "Application already exists";
        }



    }
}

