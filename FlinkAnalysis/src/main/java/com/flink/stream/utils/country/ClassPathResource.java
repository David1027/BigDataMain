package com.flink.stream.utils.country;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.Resource;

/** Created by IntelliJ IDEA. User: Lijie HelloBox@outlook.com Date: 2019/5/31 Time: 13:48 */
public class ClassPathResource extends AbstractFileResolvingResource {
  private final String path;
  private ClassLoader classLoader;
  private Class<?> clazz;

  public ClassPathResource(String path) {
    this(path, (ClassLoader) null);
  }

  public ClassPathResource(String path, ClassLoader classLoader) {
    String pathToUse = MyStringUtils.cleanPath(path);
    if (pathToUse.startsWith("/")) {
      pathToUse = pathToUse.substring(1);
    }

    this.path = pathToUse;
    this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
  }

  public ClassPathResource(String path, Class<?> clazz) {
    this.path = MyStringUtils.cleanPath(path);
    this.clazz = clazz;
  }

  /** @deprecated */
  @Deprecated
  protected ClassPathResource(String path, ClassLoader classLoader, Class<?> clazz) {
    this.path = MyStringUtils.cleanPath(path);
    this.classLoader = classLoader;
    this.clazz = clazz;
  }

  public final String getPath() {
    return this.path;
  }

  public final ClassLoader getClassLoader() {
    return this.clazz != null ? this.clazz.getClassLoader() : this.classLoader;
  }

  @Override
  public boolean exists() {
    return this.resolveURL() != null;
  }

  protected URL resolveURL() {
    if (this.clazz != null) {
      return this.clazz.getResource(this.path);
    } else {
      return this.classLoader != null
          ? this.classLoader.getResource(this.path)
          : ClassLoader.getSystemResource(this.path);
    }
  }

  @Override
  public InputStream getInputStream() throws IOException {
    InputStream is;
    if (this.clazz != null) {
      is = this.clazz.getResourceAsStream(this.path);
    } else if (this.classLoader != null) {
      is = this.classLoader.getResourceAsStream(this.path);
    } else {
      is = ClassLoader.getSystemResourceAsStream(this.path);
    }

    if (is == null) {
      throw new FileNotFoundException(
          this.getDescription() + " cannot be opened because it does not exist");
    } else {
      return is;
    }
  }

  @Override
  public URL getURL() throws IOException {
    URL url = this.resolveURL();
    if (url == null) {
      throw new FileNotFoundException(
          this.getDescription() + " cannot be resolved to URL because it does not exist");
    } else {
      return url;
    }
  }

  @Override
  public Resource createRelative(String relativePath) {
    String pathToUse = MyStringUtils.applyRelativePath(this.path, relativePath);
    return this.clazz != null
        ? new ClassPathResource(pathToUse, this.clazz)
        : new ClassPathResource(pathToUse, this.classLoader);
  }

  @Override
  public String getFilename() {
    return MyStringUtils.getFilename(this.path);
  }

  @Override
  public String getDescription() {
    StringBuilder builder = new StringBuilder("class path resource [");
    String pathToUse = this.path;
    if (this.clazz != null && !pathToUse.startsWith("/")) {
      builder.append(ClassUtils.classPackageAsResourcePath(this.clazz));
      builder.append('/');
    }

    if (pathToUse.startsWith("/")) {
      pathToUse = pathToUse.substring(1);
    }

    builder.append(pathToUse);
    builder.append(']');
    return builder.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof ClassPathResource)) {
      return false;
    } else {
      ClassPathResource otherRes = (ClassPathResource) other;
      return this.path.equals(otherRes.path)
          && ObjectUtils.nullSafeEquals(this.classLoader, otherRes.classLoader)
          && ObjectUtils.nullSafeEquals(this.clazz, otherRes.clazz);
    }
  }

  @Override
  public int hashCode() {
    return this.path.hashCode();
  }
}
