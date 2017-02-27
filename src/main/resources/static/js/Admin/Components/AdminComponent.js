function getCounts(self) {
  axios
  .get('users/admin-info')
  .then(function(response) {
    self.setState({ counts : response.data});
    console.log(response.data);
  })
  .catch(function(err){
    console.error('AdminComponent.onHandleLogout.axios',err);
  });
}

var AdminComponent = React.createClass({
  getInitialState: function() {
    return {
      adminIsLogged : false,
      counts : [],
    };
  },
  componentWillMount: function() {
    getCounts(this);
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
  componentDidMount: function() {
    EventEmitter.subscribe(this, this.onLogCounty, 'LogCounty');
  },
  componentWillUnmount: function() {
    EventEmitter.unsubscribe(this);
  },
  onLogCounty : function(){
    getCounts(this);
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
    document.title = 'Administratoriaus vaizdas - Rinkimai 2017';
    if (this.state.adminIsLogged == false) {
      return <div><img src='./Images/loading.gif'/></div>;
    }
    var c = this.state.counts;
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
                    <a href='#a' id="logout-button"  onClick={this.onHandleLogout}>
                      Atsijungti &nbsp;<i className="fa fa-sign-out"></i>
                    </a>
                  </li>
              </ul>

              <div className="navbar-default sidebar" role="navigation">
                  <div className="sidebar-nav navbar-collapse">
                      <ul className="nav" id="side-menu">
                          <li className="sidebar-search">
                          </li>
                          <li><a href="#/admin"><i className="fa fa-dashboard fa-fw"></i> Administratoriaus panele</a>

                          </li>
                          <li><a href="#/admin/county" id="county-button"><i className="fa fa-expand" aria-hidden="true"></i> Apygardos ({c[0]})</a></li>
                          <li><a href="#/admin/district" id="district-button"><i className="fa fa-compress" aria-hidden="true"></i> Apylinkės ({c[1]})</a></li>
                          <li><a href="#/admin/representative" id="representative-button"><i className="fa fa-address-book-o " aria-hidden="true"></i> Apylinkių atstovai ({c[2]})</a></li>
                          <li><a href="#/admin/candidate" id="candidates-button"><i className="fa fa-user" aria-hidden="true"></i> Kandidatai ({c[4]})</a></li>
                          <li><a href="#/admin/party" id="party-button"> <i className="fa fa-users" aria-hidden="true"></i> Partijos ({c[3]})</a></li>
                          <li><a href="#/admin/results" id="results-button"> <i className="fa fa-line-chart" aria-hidden="true"></i> Rezultatai</a></li>
                      </ul>
                  </div>
              </div>
          </nav>

          <div id="page-wrapper">
              <br/>
              <div className="row">
                  <div className="col-lg-12">
                      {this.props.children}
                      <div className="panel panel-default">
                          <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
                              <h4 style={{display:'inline'}}>
                                <i className="fa fa-clock-o fa-fw"></i>
                                Rinkimų sistemos registravimo tvarka
                              </h4>
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
