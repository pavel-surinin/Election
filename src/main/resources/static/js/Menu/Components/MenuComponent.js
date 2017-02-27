var logoStyle ={
  height: '24',
  display: 'inline',
  paddingRight: '7px'
};
var MenuComponent = React.createClass({
  render: function() {
    return (
     <div style={{paddingTop : '50px'}} className="nav-wrapper">
         <nav className="navbar navbar-inverse navbar-fixed-top">
         <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
          <span className="sr-only">Toggle navigation</span>
          <span className="icon-bar"></span>
          <span className="icon-bar"></span>
          <span className="icon-bar"></span>
        </button>
          <div className="container-fluid">
            <div className="navbar-header">
              <a className="navbar-brand" href="#"> <img style={logoStyle} src='images/favoico.png'/> Rinkėjų puslapis</a>
            </div>
            <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul className="nav navbar-nav">
              <li className="active"><a href="#">Pradinis</a></li>
              <li><a href="#/candidates">Kandidatai</a></li>
              <li><a href="#/parties">Partijos</a></li>
              <li><a href="#/counties">Apygardos</a></li>
              <li><a href="#/results">Rezultatai</a></li>
            </ul>
            </div>
          </div>
        </nav>
         <div className="col-md-10 col-md-offset-1 col-sm-12 col-xs-12 children">
             {this.props.children}
         </div>
     </div>

    );
  }
});

window.MenuComponent = MenuComponent;
