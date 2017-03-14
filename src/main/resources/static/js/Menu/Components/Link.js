function getName(self,element,id){
  axios
  .get('/' + element + '/' + id + '/name')
  .then(function(response){
    self.setState({l2 : response.data});
  }).catch(function(err){
    console.error(err);
  });
}

function getL3(self,id){
  axios
  .get('/district/' + id + '/name')
  .then(function(response){
    self.setState({l3 : response.data});
  }).catch(function(err){
    console.error(err);
  });
}

var Link = React.createClass({
  getInitialState: function() {
    return {
      l2:'',
      l3:'',
    };
  },
  componentDidMount: function() {
    var res = this.props.location.split('/');
    if (res[1] == this.props.href) {
      if (res[2] != undefined) {
        {getName(this,res[1],res[2]);}
        if (res[3] != undefined) {
          getL3(this,res[3]);
        }
      }
    }
  },
  componentWillReceiveProps: function(nextProps) {
    var res = nextProps.location.split('/');
    if (res[1] == this.props.href) {
      if (res[2] != undefined) {
        {getName(this,res[1],res[2]);}
        if (res[3] != undefined) {
          getL3(this,res[3]);
        }
      }
    }
  },
  render: function() {
    var res = this.props.location.split('/');
    var linkStyle = '';
    var countyName ='';
    var l3 = '';
    var s1 = '';
    var s2 = '';
    var first = {};
    if (res[1] == this.props.href) {
      linkStyle = 'active';
      if (res[2] != undefined && res[1] != 'candidate') {
        first = {paddingLeft : '10px', paddingRight : '10px'};
        countyName =<a style={{paddingLeft : '0px',paddingRight : '10px'}} href={'#/' + res[1] +'/' + res[2]}> <i className="fa fa-caret-right" aria-hidden="true"></i> {this.state.l2} </a>;
        if (res[3] != undefined) {
          l3 =<a style={{paddingLeft : '0px',paddingRight : '10px'}}> <i className="fa fa-caret-right" aria-hidden="true"></i> {this.state.l3} </a>;
        }
      }
    }
    return (
      <li className={linkStyle}>
        {s1}
          <a style={first} href={'#/' + this.props.href}>{this.props.linkName}</a>
          {countyName}
          {l3}
        {s2}
      </li>
    );
  }

});

window.Link = Link;
