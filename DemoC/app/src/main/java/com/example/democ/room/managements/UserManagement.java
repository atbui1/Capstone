package com.example.democ.room.managements;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.democ.room.daos.UserDao;
import com.example.democ.room.databases.CapstoneDatabase;
import com.example.democ.room.entities.User;

public class UserManagement {

    private UserDao mUserDao;
    private Context mApplication;

    public UserManagement(Context mApplication) {
        this.mApplication = mApplication;
        CapstoneDatabase capstoneDatabase = CapstoneDatabase.getDatabase(mApplication);
        mUserDao = capstoneDatabase.userDao();
    }

    public interface OnDataCallBackUser {
        void onDataSuccess(User user);
        void onDataFail();
    }

    public LiveData<User> getUserInfo() {
        return mUserDao.getUserInfo();
    }

    //add User
    public void addUser(User user, OnDataCallBackUser listener) {
        AddUserAsync addUserAsync = new AddUserAsync(mUserDao, listener);
        addUserAsync.execute(user);
    }
    private class AddUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;
        private OnDataCallBackUser mListener;

        public AddUserAsync(UserDao mUserDao, OnDataCallBackUser mListener) {
            this.mUserDao = mUserDao;
            this.mListener = mListener;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.addUser(users);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.onDataSuccess(null);
        }
    }

    //delete user
    public void deleteUser(OnDataCallBackUser listener) {
        DeleteUserAsync deleteUserAsync = new DeleteUserAsync(mUserDao, listener);
        deleteUserAsync.execute();
    }
    private class DeleteUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;
        private OnDataCallBackUser mListener;

        public DeleteUserAsync(UserDao mUserDao, OnDataCallBackUser mListener) {
            this.mUserDao = mUserDao;
            this.mListener = mListener;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.deleteUser();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.onDataSuccess(null);
        }
    }

    //getmUserInfo
    public void getmUserInfo(OnDataCallBackUser listener) {
        GetUserInfoAsync getUserInfoAsync = new GetUserInfoAsync(mUserDao, listener);
        getUserInfoAsync.execute();
    }
    private class GetUserInfoAsync extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;
        private OnDataCallBackUser mListener;
        private User mUser;

        public GetUserInfoAsync(UserDao mUserDao, OnDataCallBackUser mListener) {
            this.mUserDao = mUserDao;
            this.mListener = mListener;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUser = mUserDao.getmUserInfo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            super.onPostExecute(aVoid);
            if(mUser != null) {
                mListener.onDataSuccess(mUser);
            } else {
                mListener.onDataFail();
            }
        }
    }

    //selectUser - check exist account
    public void selectUser(OnDataCallBackUser listener) {
        SelectUserAsync selectUserAsync = new SelectUserAsync(mUserDao, listener);
        selectUserAsync.execute();
    }
    private class SelectUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;
        private OnDataCallBackUser mListener;
        private User mUser;

        public SelectUserAsync(UserDao mUserDao, OnDataCallBackUser mListener) {
            this.mUserDao = mUserDao;
            this.mListener = mListener;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUser = mUserDao.getmUserInfo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            super.onPostExecute(aVoid);
            if (mUser != null){
                mListener.onDataSuccess(mUser);
            } else {
                mListener.onDataFail();
            }
        }
    }
}
