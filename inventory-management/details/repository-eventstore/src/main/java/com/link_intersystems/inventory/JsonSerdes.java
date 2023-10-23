package com.link_intersystems.inventory;

import com.link_intersystems.beans.*;
import org.json.JSONObject;

import java.io.*;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

public class JsonSerdes implements Serdes {

    private BeansFactory beansFactory = BeansFactory.getDefault();

    @Override
    public byte[] serialize(Object event) {
        Bean<Object> bean = beansFactory.createBean(event, Object.class);
        JSONObject jsonObject = new JSONObject(new BeanMapDecorator(bean));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        write(jsonObject, bout);

        return bout.toByteArray();
    }

    void write(JSONObject jsonObject, OutputStream out) {
        try (Writer writer = new OutputStreamWriter(out, UTF_8)) {
            jsonObject.write(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> type, byte[] data) {
        BeanClass<?> beanClass = beansFactory.createBeanClass(type);

        Bean<?> bean = beanClass.newBeanInstance();
        JSONObject jsonObject = new JSONObject(new String(data, UTF_8));
        Map<String, Object> map = jsonObject.toMap();

        PropertyList properties = bean.getAllProperties();

        for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
            String propertyName = mapEntry.getKey();
            Property property = properties.getByName(propertyName);
            property.setValue(mapEntry.getValue());
        }

        return (T) bean.getBeanObject();
    }
}
