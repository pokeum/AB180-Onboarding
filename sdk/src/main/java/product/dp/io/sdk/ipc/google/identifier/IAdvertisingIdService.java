package product.dp.io.sdk.ipc.google.identifier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAdvertisingIdService extends IInterface {
    /**
     * Default implementation for IAdvertisingIdService.
     */
    public static class Default implements IAdvertisingIdService {
        @Override
        public String getId() throws RemoteException { return null; }

        @Override
        public boolean isLimitAdTrackingEnabled() throws RemoteException { return false; }

        @Override
        public boolean isLimitAdTrackingEnabledWithParam(boolean paramBoolean) throws RemoteException { return false; }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends Binder implements IAdvertisingIdService {
        private static final String DESCRIPTOR = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.google.android.gms.ads.identifier.internal.IAdvertisingIdService interface,
         * generating a proxy if needed.
         */
        public static IAdvertisingIdService asInterface(IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IAdvertisingIdService))) {
                return ((IAdvertisingIdService) iin);
            }
            return new IAdvertisingIdService.Stub.Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getId: {
                    data.enforceInterface(descriptor);
                    String _result = this.getId();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_isLimitAdTrackingEnabled: {
                    data.enforceInterface(descriptor);
                    boolean _result = this.isLimitAdTrackingEnabled();
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                case TRANSACTION_isLimitAdTrackingEnabledWithParam: {
                    data.enforceInterface(descriptor);
                    boolean _arg0;
                    _arg0 = (0 != data.readInt());
                    boolean _result = this.isLimitAdTrackingEnabledWithParam(_arg0);
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IAdvertisingIdService {
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
            public String getId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getId, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getId();
                    }
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public boolean isLimitAdTrackingEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_isLimitAdTrackingEnabled, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().isLimitAdTrackingEnabled();
                    }
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public boolean isLimitAdTrackingEnabledWithParam(boolean paramBoolean) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(((paramBoolean) ? (1) : (0)));
                    boolean _status = mRemote.transact(Stub.TRANSACTION_isLimitAdTrackingEnabledWithParam, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().isLimitAdTrackingEnabledWithParam(paramBoolean);
                    }
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public static IAdvertisingIdService sDefaultImpl;
        }

        static final int TRANSACTION_getId = (IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_isLimitAdTrackingEnabled = (IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_isLimitAdTrackingEnabledWithParam = (IBinder.FIRST_CALL_TRANSACTION + 2);

        public static boolean setDefaultImpl(IAdvertisingIdService impl) {
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

        public static IAdvertisingIdService getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    public String getId() throws RemoteException;

    public boolean isLimitAdTrackingEnabled() throws RemoteException;

    public boolean isLimitAdTrackingEnabledWithParam(boolean paramBoolean) throws RemoteException;
}