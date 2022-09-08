import { Component, OnInit } from '@angular/core';
import myAppConfig from '../config/my-app-config';
import OktaSignIn from '@okta/okta-signin-widget';
import { OktaAuthService } from '@okta/okta-angular';
import {Tokens} from '@okta/okta-auth-js';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  oktaSignin: any;
  constructor(private oktaAuthService:OktaAuthService) {
    this.oktaSignin = new OktaSignIn({
      logo: '',
      features: {
        registration: true
      },
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
  /* ngOnInit(): void {
    this.oktaSignin.renderEl({
      el: '#okta-sign-in-widget'},
      (response: any) => {
        if(response.status === 'SUCCESS'){
          this.oktaAuthService.signInWithRedirect();
          
        }
      },
      (error:any)=>{
        throw error;
      })
  } */

  async ngOnInit() {
    const  originalUri = this.oktaAuthService.getOriginalUri();
    if(!originalUri){
      this.oktaAuthService.setOriginalUri("/home");
    }
    const token:Tokens = await
    this.oktaSignin.showSignInToGetTokens({
      el: '#okta-sign-in-widget',
      });
      this.oktaAuthService.handleLoginRedirect(token);
      const userClaims = this.oktaAuthService.getUser();
      console.log("User is: "+(await userClaims).name);
      this.oktaSignin.hide();
      window.location.reload();
  }
}