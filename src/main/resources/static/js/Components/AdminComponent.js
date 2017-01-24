var AdminComponent = React.createClass({
  getInitialState: function() {
    return {
      adminIsLogged : false,
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
    .get('/users/logged')
    .then(function(response) {
      if (response.data == 'admin') {
        self.setState({adminIsLogged : true});
      } else {
        self.setState({adminIsLogged : false});
        self.context.router.push('/login');
      }
    })
    .catch(function(err){
      console.error('AdminComponent.onHandleLogout.axios',err);
    });

  },
  onHandleLogout : function(){
    var self = this;
    axios
    .get('/users/logout')
    .then(function(response) {
      self.context.router.push('/');
    })
    .catch(function(err){
      console.error('AdminComponent.onHandleLogout.axios',err);
    });
  } ,


  render: function() {
    if (this.state.adminIsLogged == false) {
      return <div><img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/></div>;
    }
    return (
      <div>
      <div id="wrapper">
          <nav className="navbar navbar-default navbar-static-top" role="navigation" style={{marginBottom: 0}}>
              <div className="navbar-header">
                  <button type="button" className="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                      <span className="sr-only">Toggle navigation</span>
                      <span className="icon-bar"></span>
                      <span className="icon-bar"></span>
                      <span className="icon-bar"></span>
                  </button>
                  <a className="navbar-brand">Rinkimų Sistema [BytecodeReaders]v1.0</a>
              </div>

              <ul className="nav navbar-top-links navbar-right">
                <li>
                    <a onClick={this.onHandleLogout}>
                      Atsijungti &nbsp;<i className="fa fa-sign-out"></i>
                    </a>
                  </li>
              </ul>

              <div className="navbar-default sidebar" role="navigation">
                  <div className="sidebar-nav navbar-collapse">
                      <ul className="nav" id="side-menu">
                          <li className="sidebar-search">
                          </li>
                          <li><a href="#/admin"><i className="fa fa-dashboard fa-fw"></i>Administratoriaus panele</a></li>
                          <li><a href="#/admin/county"><i className="fa fa-expand" aria-hidden="true"></i> Apygardos</a></li>
                          <li><a href="#/admin/district"><i className="fa fa-compress" aria-hidden="true"></i> Apylinkės</a></li>
                          <li><a href="#/admin/representative"><i className="fa fa-address-book-o " aria-hidden="true"></i> Apylinkių atstovai</a></li>
                          <li><a href="#/admin/candidate"><i className="fa fa-user" aria-hidden="true"></i> Kandidatai</a></li>
                          <li><a href="#/admin/party"> <i className="fa fa-users" aria-hidden="true"></i> Partijos</a></li>
                      </ul>
                  </div>
              </div>
          </nav>

          <div id="page-wrapper">
              <div className="row">
                  <div className="col-lg-12">
                      <h3 className="page-header">Dashbordas</h3>
                  </div>
              </div>
              <div className="row">
                  <div className="col-lg-3 col-md-6">
                      <div className="panel panel-primary">
                          <div className="panel-heading">
                              <div className="row">
                                  <div className="col-xs-3">
                                      <i className="fa fa-expand fa-4x"></i>
                                  </div>
                                  <div className="col-xs-9 text-right">
                                      <div className="huge">26</div>
                                      <div>Apygardos</div>
                                  </div>
                              </div>
                          </div>
                          <a href="/#/admin/county/create">
                              <div className="panel-footer">
                                  <span className="pull-left">Pridėti naują</span>
                                  <span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
                                  <div className="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
                  <div className="col-lg-3 col-md-6">
                      <div className="panel panel-green">
                          <div className="panel-heading">
                              <div className="row">
                                  <div className="col-xs-3">
                                      <i className="fa fa-compress fa-4x"></i>
                                  </div>
                                  <div className="col-xs-9 text-right">
                                      <div className="huge">12</div>
                                      <div>Apylinkių</div>
                                  </div>
                              </div>
                          </div>
                          <a href="/#/admin/district/create">
                              <div className="panel-footer">
                                  <span className="pull-left">Pridėti naują</span>
                                  <span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
                                  <div className="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
                  <div className="col-lg-3 col-md-6">
                      <div className="panel panel-yellow">
                          <div className="panel-heading">
                              <div className="row">
                                  <div className="col-xs-3">
                                      <i className="fa fa-user fa-4x"></i>
                                  </div>
                                  <div className="col-xs-9 text-right">
                                      <div className="huge">124</div>
                                      <div>Kandidatų</div>
                                  </div>
                              </div>
                          </div>
                          <a href="#/admin/candidate">
                              <div className="panel-footer">
                                  <span className="pull-left">Peržiūrėti kandidatus</span>
                                  <span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
                                  <div className="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
                  <div className="col-lg-3 col-md-6">
                      <div className="panel panel-red">
                          <div className="panel-heading">
                              <div className="row">
                                  <div className="col-xs-3">
                                      <i className="fa fa-users fa-4x"></i>
                                  </div>
                                  <div className="col-xs-9 text-right">
                                      <div className="huge">13</div>
                                      <div>Partijų</div>
                                  </div>
                              </div>
                          </div>
                          <a href="#/admin/party/create">
                              <div className="panel-footer">
                                  <span className="pull-left">Pridėti Naują</span>
                                  <span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
                                  <div className="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
              </div>
              <div className="row">
                  <div className="col-lg-12">
                      {this.props.children}
                      <div className="panel panel-default">
                          <div className="panel-heading">
                              <i className="fa fa-clock-o fa-fw"></i> Rinkimų sistemos registravimo tvarka
                          </div>
                          <div className="panel-body">
                              1. Registruojama <mark>APYGARDA</mark><br/>
                              2. Registruojama <mark>APYLINKĖ</mark> ir priskiriama <mark>APYGARDA</mark><br/>
                              3. Registruojamas <mark>APYLINKĖS ATSTOVAS</mark> ir priskiriama <mark>APYLINKĖ</mark><br/>
                              4. Registruojama <mark>PARTIJA</mark> ir prisegami <mark>KANDIDATAI</mark> <kbd>*.csv</kbd> iš .csv failo.<br/>
                              5. Redaguojama <mark>APYGARDA</mark> ir jai priskiriami <mark>KANDIDATAI</mark>, balsavimui.
                          </div>
                      </div>
                  </div>
              </div>
          </div>

      </div>

      </div>
    );
  }

});

AdminComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.AdminComponent = AdminComponent;
