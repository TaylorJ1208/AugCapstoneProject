import { Component, OnInit } from '@angular/core';
import myAppConfig from '../config/my-app-config';
import OktaSignIn from '@okta/okta-signin-widget';
import { OktaAuthService } from '@okta/okta-angular';
import { AppInsightsService } from '../services/appInsights-service/app-insights.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  oktaSignin: any;
  constructor(private oktaAuthService:OktaAuthService, private appInsightsService: AppInsightsService ) {
    this.appInsightsService.logPageView('Login Page');
    this.oktaSignin = new OktaSignIn({
      logo: '',
      baseUrl: myAppConfig.oidc.issuer.split('/oauth2')[0],
      clientId: myAppConfig.oidc.clientId,
      redirectUri: myAppConfig.oidc.redirectUri,
      authParams: {
        pkce: true,
        issuer: myAppConfig.oidc.issuer,
        scopes: myAppConfig.oidc.scopes
      }
    })
  }
  ngOnInit(): void {
    this.oktaSignin.renderEl({
      el: '#okta-sign-in-widget'},
      (response: any) => {
        if(response.status === 'SUCCESS'){
          this.appInsightsService.logEvent("Successful Login");
          this.oktaAuthService.signInWithRedirect();
        }
      },
      (error:any)=>{
        throw error;
      })
  }
}