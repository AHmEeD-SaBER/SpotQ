package com.spotq.authentication.domain.usecases.signup;

import com.spotq.authentication.domain.repository.IAuthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class SignUpUseCase_Factory implements Factory<SignUpUseCase> {
  private final Provider<IAuthRepository> authRepositoryProvider;

  public SignUpUseCase_Factory(Provider<IAuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public SignUpUseCase get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static SignUpUseCase_Factory create(Provider<IAuthRepository> authRepositoryProvider) {
    return new SignUpUseCase_Factory(authRepositoryProvider);
  }

  public static SignUpUseCase newInstance(IAuthRepository authRepository) {
    return new SignUpUseCase(authRepository);
  }
}
