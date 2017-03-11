var call = null;

function checkState(state,self){
  if (state.searchFor == '' || state.searchFor.length <= 2) {
    clearTimeout(call);
  }
  if (state.searchFor != '' && state.searchFor.length > 2) {
    clearTimeout(call);
    call = setTimeout(function(){self.context.router.push('search/' + state.searchFor);},1000);
  }
}
var logoStyle ={
  height: '24px',
  display: 'inline',
  paddingRight: '7px'
};
var MenuComponent = React.createClass({
  getInitialState: function() {
    return {
      l2:'',
      searchFor : '',
    };
  },
  onSearchClick : function(event){
    if (event) {
      event.preventDefault();
    }
    var search = document.getElementById('search');
    var button = document.getElementById('search-button');
    if (search.style.width == '150px') {
      search.style.width = '0px';
      search.style.padding = '0px';
      search.style.border = '0px';
      button.style.backgroundColor = 'inherit';
      button.blur();
      if (this.state.searchFor != '') {this.context.router.push('search/' + this.state.searchFor);}
    } else  {
      search.style.width = '150px';
      search.style.padding = '5px';
      search.style.border = '1px';
      search.focus();
      button.style.backgroundColor = 'black';
    }

  },
  onHAndleSearchChange : function(event){
    this.setState({searchFor : event.target.value});
  },
  shouldComponentUpdate: function(nextProps, nextState) {
    if (this.state.searchFor == nextState.searchFor) {
      // return false;
      return true;
    } else {
      checkState(nextState, this);
      return true;
    }
  },
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
              <a className="navbar-brand" href="#"> <img style={logoStyle} src='images/favoico.png'/></a>
            </div>
            <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul className="nav navbar-nav">
                <Link location={this.props.location.pathname} href='' linkName='Pradinis' />
                <Link location={this.props.location.pathname} href='candidate' linkName='Kandidatai' />
                <Link location={this.props.location.pathname} href='party' linkName='Partijos' />
                <Link location={this.props.location.pathname} href='county' linkName='Apygardos' />
                <Link location={this.props.location.pathname} href='results' linkName='Rezultatai' />
              </ul>
              <div style={{display : 'inline-table', marginRight : '0px', paddingRight : '0px', float : 'right'}}>
                <div className="input-group my-nav-search">
                <form onSubmit={this.onSearchClick}>
                  <input onChange={this.onHAndleSearchChange} style={{width : '0px'}} id='search' type="text" className="form-control search-input" placeholder="Ieškoti..."/>
                </form>
                <span className="input-group-btn">
              <button id='search-button' onClick={this.onSearchClick} className="btn btn-default" type="button"><i className="fa fa-search" aria-hidden="true"></i></button>
              </span>
              </div>
              </div>
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

MenuComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.MenuComponent = MenuComponent;
