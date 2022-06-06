package product.dp.io.sdk.ipc.google.installreferrer;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IGetInstallReferrerService extends IInterface {

    /**
     * Default implementation for IGetInstallReferrerService.
     */
    public static class Default implements IGetInstallReferrerService {
        @Override
        public Bundle getInstallReferrer(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends Binder implements IGetInstallReferrerService {
        private static final String DESCRIPTOR = "com.google.android.finsky.externalreferrer.IGetInstallReferrerService";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an product.dp.io.sdk.ipc.google.installreferrer.IGetInstallReferrerService interface,
         * generating a proxy if needed.
         */
        public static IGetInstallReferrerService asInterface(IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IGetInstallReferrerService))) {
                return ((IGetInstallReferrerService) iin);
            }
            return new IGetInstallReferrerService.Stub.Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(descriptor);
                    return true;
                case TRANSACTION_getInstallReferrer:
                    data.enforceInterface(descriptor);
                    Bundle _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    Bundle _result = this.getInstallReferrer(_arg0);
                    reply.writeNoException();
                    if ((_result != null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetInstallReferrerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public Bundle getInstallReferrer(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((bundle != null)) {
                        _data.writeInt(1);
                        bundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getInstallReferrer, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getInstallReferrer(bundle);
                    }
                    _reply.readException();
                    if ((0 != _reply.readInt())) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public static IGetInstallReferrerService sDefaultImpl;
        }

        static final int TRANSACTION_getInstallReferrer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

        public static boolean setDefaultImpl(IGetInstallReferrerService impl) {
            // Only one user of this interface can use this function
            // at a time. This is a heuristic to detect if two different
            // users in the same process use this function.
            if (Stub.Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IGetInstallReferrerService getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    public Bundle getInstallReferrer(Bundle bundle) throws RemoteException;
}
