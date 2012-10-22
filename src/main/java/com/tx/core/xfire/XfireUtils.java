/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-22
 * <修改描述:>
 */
package com.tx.core.xfire;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-10-22]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class XfireUtils {
    
//    /**
//     * 创建WebService的客户端代理
//     * @param <T> 客户端代理类型
//     * @param clazz 客户端代理接口
//     * @param url WebService地址
//     * @param timeout 超时时间，单位为毫秒
//     * @return 对应的WebService客户端代理
//     * @throws MalformedURLException
//     */
//
//    public static <T> T createService(Class<T> clazz, String url, long timeout)
//            throws MalformedURLException {
//        return createService(clazz, url, timeout, null, null, null);
//    }
//
//    /**
//     * 创建WebService的客户端代理
//     * @param <T>客户端代理类型
//     * @param clazz客户端代理接口
//     * @param urlWebService地址
//     * @param timeout超时时间，单位为毫秒
//     * @param bind一般用於指明是使用Soap1.1还是1.2標準， SoapHttpTransport.SOAP11_HTTP_BINDING<br/>SoapHttpTransport.SOAP12_HTTP_BINDING
//     * @return 对应的WebService客户端代理
//     * @throws MalformedURLException
//     */
//    public static <T> T createService(Class<T> clazz, String url, long timeout,
//            String bind) throws MalformedURLException {
//        return createService(clazz, url, timeout, bind, null, null);
//    }
//
//    /**
//     * 创建WebService的客户端代理
//     * @param <T>客户端代理类型
//     * @param clazz客户端代理接口
//     * @param urlWebService地址
//     * @param timeout超时时间，单位为毫秒
//     * @param bind一般用於指明是使用Soap1.1还是1.2標準 SoapHttpTransport.SOAP11_HTTP_BINDING<br/> SoapHttpTransport.SOAP12_HTTP_BINDING
//     * @param username
//     *            用户名
//     * @param password
//     *            密码
//     * @return 对应的WebService客户端代理
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T createService(Class<T> clazz, String url, long timeout,
//            String bind, String username, String password)
//            throws MalformedURLException {
//
//        XFire xfire = XFireFactory.newInstance().getXFire();
//        XFireProxyFactory factory = new XFireProxyFactory(xfire);
//        // 创建Service实例
//        Service serviceModel = new ObjectServiceFactory().create(clazz);
//
//        Transport transport = null;
//
//        // 如果指明了传输协议
//        if (StringUtils.isNotEmpty(bind)) {
//            // 获取传输协议
//            TransportManager tm = xfire.getTransportManager();
//            transport = tm.getTransport(bind);
//        }
//
//        // 创建Client Proxy实例
//        T service = (transport == null ? (T) factory.create(serviceModel, url)
//                : (T) factory.create(serviceModel, transport, url));
//
//        // http 设置
//        Client client = Client.getInstance(service);
//        // 超时时间设置，单位是
//        if (timeout >= 0) {
//            client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String
//                    .valueOf(timeout));
//        }
//
//        //设置用户名和密码
//        if (username != null && password != null) {
//            client.setProperty(Channel.USERNAME, username);
//            client.setProperty(Channel.PASSWORD, password);
//        }
//
//        return service;
//    }
    
}
