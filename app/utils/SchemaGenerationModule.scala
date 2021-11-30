package utils

class SchemaGenerationModule extends com.google.inject.AbstractModule {
  override protected def configure() = {
    bind(classOf[SchemaGenerator]).asEagerSingleton()
  }
}
