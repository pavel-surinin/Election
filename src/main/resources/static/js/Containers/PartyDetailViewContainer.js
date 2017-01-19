var PartyDetailViewContainer = React.createClass({
  getInitialState: function() {
    return {
      PartyCandidateList : [],
      isLoading : true,
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
      .get('/candidate')
      .then(function(response){
        self.setState({
          PartyCandidateList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('PartyCandidateListContainer.componentWillMount.axios.get.candidate', err);
      });
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/>
        </div>
      );
    } else {
      console.log(this.state.PartyCandidateList);
      return (
        <PartyDetailViewComponent PartyCandidateList={this.state.PartyCandidateList}/>
      );
    }
  }
  });

window.PartyDetailViewContainer = PartyDetailViewContainer;
