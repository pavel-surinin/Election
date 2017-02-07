var RepresentativeComponent = React.createClass({

  render: function() {
    console.log('RepresentativeComponent children: ' , this.props.children);
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
                    <a href='#a' onClick={this.onHandleLogout}>
                      Atsijungti &nbsp;<i className="fa fa-sign-out"></i>
                    </a>
                  </li>
              </ul>

              <div className="navbar-default sidebar" role="navigation">
                  <div className="sidebar-nav navbar-collapse">
                      <ul className="nav" id="side-menu">
                          <li className="sidebar-search">
                          </li>
                          <li><a href="#/representative"><i className="fa fa-dashboard fa-fw"></i> Atstovo panele</a>

                          </li>
                          <li><a href="#/representative/single"><i className="fa fa-user" aria-hidden="true"></i> Vienmandatės apygardos</a></li>
                          <li><a href="#/representative/multi"><i className="fa fa-users" aria-hidden="true"></i> Daugiamandatės apygardos</a></li>

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
                  <div className="col-lg-12">
                      {this.props.children}
                  </div>
              </div>
          </div>

      </div>

      </div>
    );
  }

});

window.RepresentativeComponent = RepresentativeComponent;
