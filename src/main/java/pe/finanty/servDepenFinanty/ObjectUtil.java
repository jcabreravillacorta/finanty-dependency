package pe.finanty.servDepenFinanty;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ObjectUtil {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    public static Object getParent(Object obj, String atributo) {
        Object parent = null;
        Method metodo = null;
        StringBuilder sbMetodo = new StringBuilder("get");
        sbMetodo.append(WordUtils.capitalize(atributo));

        for (Method metodoTmp : obj.getClass().getMethods()) {
            if (sbMetodo.toString().equals(metodoTmp.getName())) {
                metodo = metodoTmp;
                break;
            }
        }
        try {
            parent = metodo.invoke(obj);
        } catch (InvocationTargetException ex) {
        } catch (Exception ex) {
            logger.error("InvocationTargetException ::: " + ex.getLocalizedMessage());
        }

        return parent;
    }

    public static Object getParentTree(Object obj, String atributo) {
        Object objPadre = null;
        Object objHijo = obj;
        String[] attrs = atributo.split("\\.");

        for (String attr : attrs) {
            objPadre = getParent(objHijo, attr);
            if (objPadre == null) {
                return null;
            }
            objHijo = objPadre;
        }

        return objPadre;
    }

}
