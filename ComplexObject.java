/*public class ComplexObject {
}*/
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;

public class ComplexObject implements ActionListener {
    private TransformGroup GanteliTransformGroup = new TransformGroup();
    private Transform3D treeTransform3D = new Transform3D();
    private Timer timer;
    private float angle = 0;
    int counter = 0;
    public static void main(String[] args) {
        new ComplexObject();
    }


    public ComplexObject() {
        timer = new Timer(50, this);
        timer.start();
        BranchGroup scene = createSceneGraph();
        SimpleUniverse u = new SimpleUniverse();
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
    }
    public BranchGroup createSceneGraph() {
// створюємо групу об'єктів
        BranchGroup objRoot = new BranchGroup();
// створюємо об'єкт, що будемо додавати до групи
        GanteliTransformGroup = new TransformGroup();
        GanteliTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        createGanteli();
        objRoot.addChild(GanteliTransformGroup);
// налаштовуємо освітлення
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color,
                light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);
// встановлюємо навколишнє освітлення
        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }
    private void createGanteli() {
createRuchka(0.3f, 0.05f, 0.05f, 0.25f, -0.5f, -0.1f);
        createRuchka(0.3f, 0.05f, 0.05f, 0.25f, -0.5f, -0.5f);

        createBall(0.20f, 0.52f, -0.5f, -0.1f, "", new Color3f(0.2f, 0.2f, 1.5f));
        createBall(0.20f, -0.15f, -0.5f, -0.1f, "", new Color3f(0.6f, 1.2f, 0.0f));

        createBall(0.20f, 0.52f, -0.5f, -0.5f, "", new Color3f(0.2f, 0.2f, 1.5f));
        createBall(0.20f, -0.15f, -0.5f, -0.5f, "", new Color3f(0.6f, 1.2f, 0.0f));
    }
    private void createBall(float radius, float x, float y, float z, String picture,
                            Color3f emissive) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Sphere cone = Ball.getBall(radius, picture, emissive);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cone);
        GanteliTransformGroup.addChild(tg);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(counter < 100) {
            treeTransform3D.rotY(angle);
            counter++;
        }
        else if(counter >= 100 && counter < 200) {
            treeTransform3D.rotZ(angle);
            counter++;
        }
        else{
            treeTransform3D.rotX(angle);
            counter++;
        }
        if(counter > 299){
            counter = 0;
        }
        GanteliTransformGroup.setTransform(treeTransform3D);
        angle += 0.05;
    }
    private void createRuchka(float x, float y, float z, float c1, float c2, float c3){
        Box ruchka = new Box(x, y, z, getRuchkaAppearence("", new Color3f(0.7f, 0.9f, 0.15f)));
        Vector3f vector = new Vector3f(c1, c2, c3);
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(ruchka);
        GanteliTransformGroup.addChild(tg);

    }
    private static Appearance getRuchkaAppearence(String picture, Color3f emissive) {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(0.7f, 0.9f, 0.15f);
        Color3f diffuse = new Color3f(1.2f, 1.15f, .15f);
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));  return ap;
    }
}



class Ball {
    public static Sphere getBall(float radius, String picture, Color3f emissiveColor) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Sphere(radius);
    }
    private static Appearance getXMassBallsAppearence(String picture, Color3f emissive) {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(0.2f, 0.15f, .15f);
        Color3f diffuse = new Color3f(1.2f, 1.15f, .15f);
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        if (picture != "") {
// завантажуємо текстуру
            TextureLoader loader = new TextureLoader(picture, "LUMINANCE", new
                    Container());
            Texture texture = loader.getTexture();
// задаємо властивості границі
            texture.setBoundaryModeS(Texture.WRAP);
            texture.setBoundaryModeT(Texture.WRAP);
            texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
            TextureAttributes texAttr = new TextureAttributes();
            texAttr.setTextureMode(TextureAttributes.MODULATE);
            ap.setTexture(texture);
            ap.setTextureAttributes(texAttr);
        }
        return ap;
    }
}

class Boxes {
    public static Box getBox(float radius, String picture, Color3f emissiveColor) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(0f, 0f, 0f, getBoxAppearence(picture, emissiveColor));
    }
    private static Appearance getBoxAppearence(String picture, Color3f emissive) {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(0.2f, 0.15f, .15f);
        Color3f diffuse = new Color3f(1.2f, 1.15f, .15f);
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        if (picture != "") {
// завантажуємо текстуру
            TextureLoader loader = new TextureLoader(picture, "LUMINANCE", new
                    Container());
            Texture texture = loader.getTexture();
// задаємо властивості границі
            texture.setBoundaryModeS(Texture.WRAP);
            texture.setBoundaryModeT(Texture.WRAP);
            texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
            TextureAttributes texAttr = new TextureAttributes();
            texAttr.setTextureMode(TextureAttributes.MODULATE);
            ap.setTexture(texture);
            ap.setTextureAttributes(texAttr);
        }
        return ap;
    }
}
