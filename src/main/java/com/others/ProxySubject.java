package com.others;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.proxy.Person;

/**
 * 
 * @Title: ProxySubject
 * @Description:java动态代理 生成的class文件
 * @Author: zhaotf
 * @Since:2017年6月1日 下午1:35:30
 * @Version:1.0
 */
@SuppressWarnings("serial")
public final class ProxySubject extends Proxy implements Person {

	// protected ProxySubject(InvocationHandler h) {
	// super(h);
	// // TODO Auto-generated constructor stub
	// }

	protected ProxySubject(InvocationHandler h) {
		super(h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void giveMoney() {
		// TODO Auto-generated method stub

	}

//	private static Method m17;
//	private static Method m8;
//	private static Method m18;
//	private static Method m21;
//	private static Method m5;
//	private static Method m15;
//	private static Method m7;
//	private static Method m9;
//	private static Method m16;
//	private static Method m2;
//	private static Method m1;
//	private static Method m10;
//	private static Method m4;
//	private static Method m11;
//	private static Method m19;
//	private static Method m3;
//	private static Method m0;
//	private static Method m13;
//	private static Method m20;
//	private static Method m6;
//	private static Method m12;
//	private static Method m14;
//
//	public ProxySubject(InvocationHandler paramInvocationHandler) {
//		super(paramInvocationHandler);
//	}
//
//	public final void wait(long paramLong) throws Exception {
//		try {
//			this.h.invoke(this, m17, new Object[] { Long.valueOf(paramLong) });
//			return;
//		} catch (RuntimeException localRuntimeException) {
//			throw localRuntimeException;
//		} catch (Throwable localThrowable) {
//		}
//		throw new UndeclaredThrowableException(localThrowable);
//	}
//
//	public final Object doAs(Subject paramSubject, PrivilegedAction paramPrivilegedAction)
//	    throws 
//	  {
//	    try
//	    {
//	      return (Object)this.h.invoke(this, m8, new Object[] { paramSubject, paramPrivilegedAction });
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final void wait() throws Exception {
//		try {
//			this.h.invoke(this, m18, null);
//			return;
//		} catch (RuntimeException localRuntimeException) {
//			throw localRuntimeException;
//		} catch (Throwable localThrowable) {
//		}
//		throw new UndeclaredThrowableException(localThrowable);
//	}
//
//	public final void notifyAll()
//	    throws 
//	  {
//	    try
//	    {
//	      this.h.invoke(this, m21, null);
//	      return;
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final void setReadOnly()
//	    throws 
//	  {
//	    try
//	    {
//	      this.h.invoke(this, m5, null);
//	      return;
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Subject getSubject(AccessControlContext paramAccessControlContext)
//	    throws 
//	  {
//	    try
//	    {
//	      return (Subject)this.h.invoke(this, m15, new Object[] { paramAccessControlContext });
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Object doAs(Subject paramSubject, PrivilegedExceptionAction paramPrivilegedExceptionAction)
//			throws PrivilegedActionException {
//		try {
//			return (Object) this.h.invoke(this, m7, new Object[] { paramSubject, paramPrivilegedExceptionAction });
//		} catch (RuntimeException localRuntimeException) {
//			throw localRuntimeException;
//		} catch (Throwable localThrowable) {
//		}
//		throw new UndeclaredThrowableException(localThrowable);
//	}
//
//	public final Object doAsPrivileged(Subject paramSubject, PrivilegedAction paramPrivilegedAction, AccessControlContext paramAccessControlContext)
//	    throws 
//	  {
//	    try
//	    {
//	      return (Object)this.h.invoke(this, m9, new Object[] { paramSubject, paramPrivilegedAction, paramAccessControlContext });
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final void wait(long paramLong, int paramInt) throws Exception {
//		try {
//			this.h.invoke(this, m16, new Object[] { Long.valueOf(paramLong), Integer.valueOf(paramInt) });
//			return;
//		} catch (RuntimeException localRuntimeException) {
//			throw localRuntimeException;
//		} catch (Throwable localThrowable) {
//		}
//		throw new UndeclaredThrowableException(localThrowable);
//	}
//
//	public final String toString()
//	    throws 
//	  {
//	    try
//	    {
//	      return (String)this.h.invoke(this, m2, null);
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final boolean equals(Object paramObject)
//	    throws 
//	  {
//	    try
//	    {
//	      return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Object doAsPrivileged(Subject paramSubject, PrivilegedExceptionAction paramPrivilegedExceptionAction,
//			AccessControlContext paramAccessControlContext) throws PrivilegedActionException {
//		try {
//			return (Object) this.h.invoke(this, m10,
//					new Object[] { paramSubject, paramPrivilegedExceptionAction, paramAccessControlContext });
//		} catch (RuntimeException localRuntimeException) {
//			throw localRuntimeException;
//		} catch (Throwable localThrowable) {
//		}
//		throw new UndeclaredThrowableException(localThrowable);
//	}
//
//	public final Set getPrincipals(Class paramClass)
//	    throws 
//	  {
//	    try
//	    {
//	      return (Set)this.h.invoke(this, m4, new Object[] { paramClass });
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Set getPrivateCredentials()
//	    throws 
//	  {
//	    try
//	    {
//	      return (Set)this.h.invoke(this, m11, null);
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Class getClass()
//	    throws 
//	  {
//	    try
//	    {
//	      return (Class)this.h.invoke(this, m19, null);
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Set getPrincipals()
//	    throws 
//	  {
//	    try
//	    {
//	      return (Set)this.h.invoke(this, m3, null);
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final int hashCode()
//	    throws 
//	  {
//	    try
//	    {
//	      return ((Integer)this.h.invoke(this, m0, null)).intValue();
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Set getPublicCredentials()
//	    throws 
//	  {
//	    try
//	    {
//	      return (Set)this.h.invoke(this, m13, null);
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final void notify()
//	    throws 
//	  {
//	    try
//	    {
//	      this.h.invoke(this, m20, null);
//	      return;
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final boolean isReadOnly()
//	    throws 
//	  {
//	    try
//	    {
//	      return ((Boolean)this.h.invoke(this, m6, null)).booleanValue();
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Set getPrivateCredentials(Class paramClass)
//	    throws 
//	  {
//	    try
//	    {
//	      return (Set)this.h.invoke(this, m12, new Object[] { paramClass });
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	public final Set getPublicCredentials(Class paramClass)
//	    throws 
//	  {
//	    try
//	    {
//	      return (Set)this.h.invoke(this, m14, new Object[] { paramClass });
//	    }
//	    catch (RuntimeException localRuntimeException)
//	    {
//	      throw localRuntimeException;
//	    }
//	    catch (Throwable localThrowable)
//	    {
//	    }
//	    throw new UndeclaredThrowableException(localThrowable);
//	  }
//
//	static {
//		try {
//			m17 = Class.forName("javax.security.auth.Subject").getMethod("wait", new Class[] { Long.TYPE });
//			m8 = Class.forName("javax.security.auth.Subject").getMethod("doAs", new Class[] {
//					Class.forName("javax.security.auth.Subject"), Class.forName("java.security.PrivilegedAction") });
//			m18 = Class.forName("javax.security.auth.Subject").getMethod("wait", new Class[0]);
//			m21 = Class.forName("javax.security.auth.Subject").getMethod("notifyAll", new Class[0]);
//			m5 = Class.forName("javax.security.auth.Subject").getMethod("setReadOnly", new Class[0]);
//			m15 = Class.forName("javax.security.auth.Subject").getMethod("getSubject",
//					new Class[] { Class.forName("java.security.AccessControlContext") });
//			m7 = Class.forName("javax.security.auth.Subject").getMethod("doAs",
//					new Class[] { Class.forName("javax.security.auth.Subject"),
//							Class.forName("java.security.PrivilegedExceptionAction") });
//			m9 = Class.forName("javax.security.auth.Subject").getMethod("doAsPrivileged",
//					new Class[] { Class.forName("javax.security.auth.Subject"),
//							Class.forName("java.security.PrivilegedAction"),
//							Class.forName("java.security.AccessControlContext") });
//			m16 = Class.forName("javax.security.auth.Subject").getMethod("wait",
//					new Class[] { Long.TYPE, Integer.TYPE });
//			m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
//			m1 = Class.forName("java.lang.Object").getMethod("equals",
//					new Class[] { Class.forName("java.lang.Object") });
//			m10 = Class.forName("javax.security.auth.Subject").getMethod("doAsPrivileged",
//					new Class[] { Class.forName("javax.security.auth.Subject"),
//							Class.forName("java.security.PrivilegedExceptionAction"),
//							Class.forName("java.security.AccessControlContext") });
//			m4 = Class.forName("javax.security.auth.Subject").getMethod("getPrincipals",
//					new Class[] { Class.forName("java.lang.Class") });
//			m11 = Class.forName("javax.security.auth.Subject").getMethod("getPrivateCredentials", new Class[0]);
//			m19 = Class.forName("javax.security.auth.Subject").getMethod("getClass", new Class[0]);
//			m3 = Class.forName("javax.security.auth.Subject").getMethod("getPrincipals", new Class[0]);
//			m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
//			m13 = Class.forName("javax.security.auth.Subject").getMethod("getPublicCredentials", new Class[0]);
//			m20 = Class.forName("javax.security.auth.Subject").getMethod("notify", new Class[0]);
//			m6 = Class.forName("javax.security.auth.Subject").getMethod("isReadOnly", new Class[0]);
//			m12 = Class.forName("javax.security.auth.Subject").getMethod("getPrivateCredentials",
//					new Class[] { Class.forName("java.lang.Class") });
//			m14 = Class.forName("javax.security.auth.Subject").getMethod("getPublicCredentials",
//					new Class[] { Class.forName("java.lang.Class") });
//			return;
//		} catch (NoSuchMethodException localNoSuchMethodException) {
//			throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
//		} catch (ClassNotFoundException localClassNotFoundException) {
//		}
//		throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
//	}
}
