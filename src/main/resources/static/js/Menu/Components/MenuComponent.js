var MenuComponent = React.createClass({
  render: function() {
    return (
     <div style={{paddingTop : '50px'}} className="nav-wrapper">
         <nav className="navbar navbar-inverse navbar-fixed-top">
          <div className="container-fluid">
            <div className="navbar-header">
              <a className="navbar-brand" href="#"> <i className="fa fa-bar-chart" aria-hidden="true"></i> Rinkėjų puslapis</a>
            </div>
            <ul className="nav navbar-nav">
              <li className="active"><a href="#">Pradinis</a></li>
              <li><a href="#/candidates">Kandidatai</a></li>
              <li><a href="#/parties">Partijos</a></li>
              <li><a href="#/counties">Apygardos</a></li>
              <li><a href="#/results">Rezultatai</a></li>
            </ul>
          </div>
        </nav>
         <div className="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1 children">
             {this.props.children}
         </div>
     </div>

    );
  }
});

window.MenuComponent = MenuComponent;
