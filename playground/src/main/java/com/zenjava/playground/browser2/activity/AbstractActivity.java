package com.zenjava.playground.browser2.activity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class AbstractActivity<NodeType extends Node> implements HasNode<NodeType>, Activatable
{
    private NodeType node;
    private BooleanProperty active;

    protected AbstractActivity()
    {
        this.active = new SimpleBooleanProperty();
    }

    public NodeType getNode()
    {
        return node;
    }

    protected void setNode(NodeType node)
    {
        this.node = node;
    }

    public void activate(Map<String, Object> parameters)
    {
        processActivationAnnotations(parameters);
        active.set(true);
    }

    public void deactivate()
    {
        active.set(false);
    }

    public boolean isActive()
    {
        return this.active.get();
    }

    public ReadOnlyBooleanProperty activeProperty()
    {
        return this.active;
    }

    protected void processActivationAnnotations(Map<String, Object> parameters)
    {
        Method[] methods = getClass().getMethods();
        for (Method method : methods)
        {
            if (method.getAnnotation(Activation.class) != null)
            {
                try
                {
                    Object[] params = new Object[method.getParameterTypes().length];
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    for (int i = 0; i < parameterTypes.length; i++)
                    {
                        Class<?> paramType = parameterTypes[i];
                        for (Annotation annotation : parameterAnnotations[i])
                        {
                            if (annotation instanceof ActivationParam)
                            {
                                params[i] = parameters.get(((ActivationParam)annotation).value());
                            }
                        }
                        ActivationParam annotation = paramType.getAnnotation(ActivationParam.class);

                    }
                    method.invoke(this, params);
                }
                catch (Exception e)
                {
                    throw new ActivationException(String.format("Failed to call custom activation method '%s'", method.getName()), e);
                }
            }
        }
    }
}
